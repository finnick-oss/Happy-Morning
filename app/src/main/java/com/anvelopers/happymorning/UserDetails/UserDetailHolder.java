package com.anvelopers.happymorning.UserDetails;

public class UserDetailHolder {  //class

    //Instance variable

    private String Name,UserName,Gender,language;

    //Constructor

    public UserDetailHolder(String name, String userName, String gender,String language) {
        Name = name;
        UserName = userName;
        Gender = gender;
        this.language = language;
    }

    //Getters and setters

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getLanguage() { return language;  }

    public void setLanguage(String language) { this.language = language;  }
}  //setter
