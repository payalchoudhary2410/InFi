package com.example.infi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText Phone_No;
    private EditText OTP;
    private Button vc;
    private Button Login;
    private TextView Sign;
    private FirebaseAuth mAuth;
   private  String codeSent;
    private ProgressDialog progressDialog;
    private TextView contactUs;
    private String phoneNumber;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Phone_No = (EditText) findViewById(R.id.username);
        OTP = (EditText) findViewById(R.id.password);
        vc = (Button) findViewById(R.id.btnotp);
        Login = (Button) findViewById(R.id.login);
        Sign=(TextView) findViewById(R.id.signup);
        contactUs=(TextView) findViewById(R.id.contact);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        progressDialog=new ProgressDialog(this);

        if(user!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
        }






         vc.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {



                    SendVerificationCode();



            }
         });






        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate(OTP.getText().toString());
            }
        });

        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signupp();


            }
        });

        contactUs.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent contactUs_intent=new Intent (MainActivity.this, ContactUsActivity.class);
                        startActivity(contactUs_intent);
                    }
                }
        );




    }

    private void Signupp(){
        Intent inten = new Intent(this, RegistrationActivity.class);
        startActivity(inten);
    }

    private void SendVerificationCode()
    {
        phoneNumber = Phone_No.getText().toString();
        if (phoneNumber.length() == 0) {
            Phone_No.setError("Field cannot be empty");
            Phone_No.requestFocus();
        } else if (phoneNumber.length() != 13) {


            Phone_No.setError("Invalid number");
            Phone_No.requestFocus();
        }
        else {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    MainActivity.this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallback
        }


    }

    private void Validate(String otpp) {

        progressDialog.setMessage("Wait until the login is complete");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, otpp);
        signInWithPhoneAuthCredential(credential);
    }


        private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                progressDialog.dismiss();
                                startActivity(new Intent(MainActivity.this, SecondActivity.class));
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();


                            }
                            else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                                    Toast.makeText(MainActivity.this, "Incorrect Verification Code", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();


                            }
                        }
                    });
        }










    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {



        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSent=s;
        }
    };




}
