package com.example.chatapp;

public class Users {
    private String userName;
    private String userID;

    public Users(){}
    public Users(String userName , String userID){
        this.userName = userName;
        this.userID = userID;
    }

    public String getUserName(){return userName;}
    public void setUserName (String userName){this.userName = userName;}
    public String getUserID(){return userID;}
    public void setUserID(){this.userID = userID;}

}
