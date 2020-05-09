package com.example.infi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userPassword, userEmail, mobile, bdate, roll;
    private Button reg;
    private TextView InfoLog;
    private FirebaseAuth firebaseAuth;
    public TextView contactUsReg, terms;
    private CheckBox acceptTnC;

    DatabaseReference databaseuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupID();
        final String user_name=userName.getText().toString().trim();

        reg=(Button)findViewById(R.id.ets);
        contactUsReg=(TextView) findViewById(R.id.contactReg);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseuser= FirebaseDatabase.getInstance().getReference("Users");

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                if(validate()){
                    final String user_email=userEmail.getText().toString().trim();
                    String user_password=userPassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                Users users= new Users(user_name, user_email);
                                FirebaseUser user=firebaseAuth.getCurrentUser();
                                databaseuser.child(user.getUid()).setValue(users);






                                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                                finish();

                            }
                            else
                                Toast.makeText(RegistrationActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();







                        }
                    });

                }
            }
        });
        InfoLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });

        contactUsReg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent contactUs_intent=new Intent (RegistrationActivity.this, ContactUsActivity.class);
                        startActivity(contactUs_intent);
                    }
                }
        );


    }


    private void setupID() {
        userName = (EditText) findViewById(R.id.etn);
        userPassword = (EditText) findViewById(R.id.etp);
        userEmail = (EditText) findViewById(R.id.ete);
        reg = (Button) findViewById(R.id.ets);
        InfoLog = (TextView) findViewById(R.id.eti);
    }

    private Boolean validate() {
        Boolean ans = false;
        String name = userName.getText().toString();
        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else ans = true;

        return ans;
    }



}
