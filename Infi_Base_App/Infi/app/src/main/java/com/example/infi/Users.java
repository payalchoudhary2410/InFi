package com.example.infi;

class Users {



    String userName;
    String userEmail;

    public Users(){

   }

    public Users( String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }





    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
