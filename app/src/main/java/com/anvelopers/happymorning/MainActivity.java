package com.anvelopers.happymorning;  //Package

//Imported packages and classes

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.anvelopers.happymorning.HomeActivity.UserHomeActivity;
import com.anvelopers.happymorning.SharedPreference.AutoLogin;
import com.anvelopers.happymorning.SliderView.OnboardingActivity;
import com.anvelopers.happymorning.language.LanguageActivity;


public class MainActivity extends AppCompatActivity {  //MainActivity class

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //OnCreate method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Shared preference object for auto login

        final AutoLogin shared = new AutoLogin(MainActivity.this);


        //Handler Object
        Handler timer=new Handler();
        //calling method
        timer.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(shared.getUserName().length()==0){
                //will redirect with after splash screen
                Intent intent = new Intent(MainActivity.this,OnboardingActivity.class);
                startActivity(intent);
                finish();
                }
                else if(shared.getLanguage().equals("none")){

                    //If user haven't selected language

                    Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
                    intent.putExtra("connectType","ShareType");
                    startActivity(intent);
                    finish();
                }
                else
                {

                    Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                    intent.putExtra("connectType","ShareType");
                    startActivity(intent);
                    finish();

                }

            }
        },2000); //giving the delay of two sec


    } //OnCreate

} //Class