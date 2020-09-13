package com.example.happymorning.language;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happymorning.ConnectivityCheck.InternetConnectivityCheck;
import com.example.happymorning.HomeActivity.UserHomeActivity;
import com.example.happymorning.MainActivity;
import com.example.happymorning.R;
import com.example.happymorning.SharedPreference.AutoLogin;
import com.example.happymorning.UserDetails.UserDetailsActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LanguageActivity extends AppCompatActivity {


    private String connectType;
    private ImageView backButton;
    private TextView typeConnect;
    private Button englishLanguage,hindiLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {  //onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        //Hooks

         englishLanguage = findViewById(R.id.english);
         hindiLanguage = findViewById(R.id.hindi);



        //Connectivity class object

        final InternetConnectivityCheck connectivityCheck = new InternetConnectivityCheck();



        //English button listener

        englishLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calling method to set User Language
                if(connectivityCheck.isNetworkAvailable(LanguageActivity.this)){
                setUserLanguage("English");
                }
                else{
                    Toast.makeText(LanguageActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hindiLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Calling method to set User Language
                if (connectivityCheck.isNetworkAvailable(LanguageActivity.this)) {
                    setUserLanguage("Hindi");
                }
                else{

                    Toast.makeText(LanguageActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Getting the value from connect type which is intent

        connectType = getIntent().getStringExtra("connectType");

        //Showing dialog box according to connect type

        if(connectType.equals("Login") || connectType.equals("Registration")) {

            //Showing successful dialog box

            successful();

        }



    }  //onCreate

    //Method to set user language

    private void setUserLanguage(String language) {

        final AutoLogin  shared = new AutoLogin(this);

        //using shared preference to get username

        String username = shared.getUserName();

        //firebase instance

        FirebaseDatabase firebaseDatabase;
        final DatabaseReference databaseReference;

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users/"+username);

        //setting value to child
        databaseReference.child("language").setValue(language);


        //saving language in shared preference

        shared.setLanguage(language);

        //Showing toast

        Toast.makeText(this, "Language selected", Toast.LENGTH_SHORT).show();


        //Using intent for redirection

        Intent intent = new Intent(this,UserHomeActivity.class);
        intent.putExtra("connectType","internet");
        startActivity(intent);
        finish();

    } //setUserLanguage



    //------------------------------------------------------------------------------------------------------


    //Method for register the user by showing dialog box

    public void successful() {


        //Showing dialog

        final Dialog dialog = new Dialog(LanguageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.successfulluser);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        //Shared preference object for auto login

        final AutoLogin shared = new AutoLogin(this);

        //Hooks

        backButton =  dialog.findViewById(R.id.backButton);
        typeConnect = dialog.findViewById(R.id.connectType);

        //Setting the type

        typeConnect.setText(connectType);

        //User will click on back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        //

    }  //registerUser




} //class