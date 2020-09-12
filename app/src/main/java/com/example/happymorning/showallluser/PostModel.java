package com.example.happymorning.showallluser;

public class PostModel {

    //taking user name to see user uploads

    private String userName,gender;

    //no argument constructor

    public  PostModel(){}  // --- > must

    //getter and setters


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
