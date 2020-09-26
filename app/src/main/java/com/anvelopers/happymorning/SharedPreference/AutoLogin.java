package com.anvelopers.happymorning.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class AutoLogin {  //class


    //to create sharePreference file
    SharedPreferences sharedPreferences;   //Shared Preferences object

    //declared instance
    private String userName,language,name,quoteUri,jokeUri;


    //getters and setters for username

    public String getUserName() {
        userName=sharedPreferences.getString("userdata","");
        return userName;
    }

    public void setUserName(String username) {
        sharedPreferences.edit().putString("userdata",username).commit();
        this.userName = username;
    }

    //getters and setters for language

    public String getLanguage() {
        language=sharedPreferences.getString("language","");
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;

        sharedPreferences.edit().putString("language", language).commit();
    }

    //getter and setters for name

    public String getName() {
        name=sharedPreferences.getString("name","");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("name", name).commit();
    }


    //getter and setter of quote uri

    public String getQuoteUri() {
        quoteUri=sharedPreferences.getString("quote","");
        return quoteUri;
    }

    public void setQuoteUri(String quoteUri) {
        this.quoteUri = quoteUri;

        sharedPreferences.edit().putString("quote", quoteUri).commit();
    }


    //getter and setter of joke uri

    public String getJokeUri() {
        jokeUri=sharedPreferences.getString("joke","");
        return jokeUri;
    }

    public void setJokeUri(String jokeUri) {
        this.jokeUri = jokeUri;

        sharedPreferences.edit().putString("joke", jokeUri).commit();
    }



    //context pass the preference to another class
    public Context context;
    //creating constructor to pass memory at runtime to the shared file
    //for one time login
    public AutoLogin(Context context) {
        sharedPreferences = context.getSharedPreferences("userInfo",context.MODE_PRIVATE);
        this.context = context;

    }  //method

    //Method to remove user

   public void removeUser(Context context){

        //

       sharedPreferences.edit().clear().commit();

    }

}  //AutoLogin class
