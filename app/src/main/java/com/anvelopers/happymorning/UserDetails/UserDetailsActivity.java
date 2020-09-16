package com.anvelopers.happymorning.UserDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anvelopers.happymorning.ConnectivityCheck.InternetConnectivityCheck;
import com.anvelopers.happymorning.HomeActivity.UserHomeActivity;
import com.anvelopers.happymorning.R;
import com.anvelopers.happymorning.SharedPreference.AutoLogin;
import com.anvelopers.happymorning.language.LanguageActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserDetailsActivity extends AppCompatActivity {

    //Instance

    Button login,register,loginUserDetails,registerUserDetails;
    ImageView backButton, backButtonLogin;
    EditText regUsername,regName,loginUsername;
    private RadioGroup userGender;
    //admob
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //onCreate Method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        //Hooks

        login = findViewById(R.id.login);
        register= findViewById(R.id.register);


        //button listener

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //calling method

                    loginUser();

            }
        });

        //button listener


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calling method

              registerUser();

            }
        });




        //-----------------------------------------------------------------------


        //Admob


        mAdView = findViewById(R.id.adView2);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);


    }  //onCreate


    //---------------------------------------------------------------------------------------------



    //Method for login the user by showing dialog box

    public void loginUser() {





        final Dialog dialog = new Dialog(UserDetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loginuser);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        //Hooks

        loginUsername = dialog.findViewById(R.id.usernameLogin);
        loginUserDetails = dialog.findViewById(R.id.loginUserByUserName);
        backButtonLogin = dialog.findViewById(R.id.backButtonLogin);

        //BackButton on click listener

        backButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        //Connectivity class object

        final InternetConnectivityCheck connectivityCheck = new InternetConnectivityCheck();


        //Setting the listener on button

        loginUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(loginUsername.getText().toString().length()>1) {

                    //checking the connectivity is available

                    if (connectivityCheck.isNetworkAvailable(UserDetailsActivity.this)) {

                        String loginUserNameInString = loginUsername.getText().toString();

                        loginUsernameTest(loginUserNameInString ,dialog);



                    } else {

                        //Toast for no internet

                        Toast.makeText(UserDetailsActivity.this, "Oops! no internet", Toast.LENGTH_SHORT).show();

                    }

                }
                else{

                    //Setting error if it is empty

                    loginUsername.setError("Username is empty");

                }

            }
        });


    }  //Method LoginUser



//------------------------------------------------------------------------------------------------------


    //Method for register the user by showing dialog box

    public void registerUser() {



        //Connectivity class object

        final InternetConnectivityCheck connectivityCheck = new InternetConnectivityCheck();


        //Showing dialog

        final Dialog dialog = new Dialog(UserDetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.registeruser);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        //Hooks
        regName = dialog.findViewById(R.id.nameRegister);
        regUsername = dialog.findViewById(R.id.usernameRegister);
        registerUserDetails = dialog.findViewById(R.id.registerUser);
        userGender = dialog.findViewById(R.id.genderDetails);
        backButton =  dialog.findViewById(R.id.backButton);


        //User will click on back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        // Uncheck or reset the radio buttons initially
        userGender.clearCheck();

        // Add the Listener to the RadioGroup
        userGender.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {
                        // Get the selected Radio Button
                        RadioButton
                                radioButton
                                = (RadioButton)group
                                .findViewById(checkedId);
                    }
                });


            registerUserDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    // When submit button is clicked,
                    // Ge the Radio Button which is set
                    // If no Radio Button is set, -1 will be returned
                    int selectedId = userGender.getCheckedRadioButtonId();

                    if(regName.getText().toString().length()<1 && regUsername.getText().toString().length()<1) {

                        regName.setError("Please fill the name");
                        regUsername.setError("Please fill the username");

                    }
                    else if(regName.getText().toString().length()<1){
                            regName.setError("Please fill the name");
                        }
                        else if(regUsername.getText().toString().length()<1){

                            regUsername.setError("Please fill the username");

                        }
                    else if (selectedId == -1) {
                        Toast.makeText(UserDetailsActivity.this,
                                "Please select the gender",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                    else
                        if(!connectivityCheck.isNetworkAvailable(UserDetailsActivity.this)){

                            Toast.makeText(UserDetailsActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                        }{

                        RadioButton radioButton
                                = (RadioButton)userGender
                                .findViewById(selectedId);

                        //SetInternetConnection----------------------

                       usernameTest(regUsername.getText().toString(),radioButton.getText().toString(),dialog);



                    }


                } //onClick Listener
            });


    }  //registerUser



//------------------------------------------------------------------------------------------------------



    //Instance variable

    boolean result;

    //Static method
    static int times=0;

    //Method to test username exist or not

    public void usernameTest(String username , final String gender, final Dialog dialog){

       times= 0;

        //String variable
        final String registerUserame = username;
        String userGender = gender;

        //Firebase database instance and reference

        FirebaseDatabase firebaseDatabase;
        final DatabaseReference databaseReference2;

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference2=firebaseDatabase.getReference("Users");
        Query checkUser = databaseReference2.orderByChild("userName").equalTo(registerUserame);


        //Testing the Username
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Condition to test username already existed

                if(!snapshot.exists() ){

                    uploadUserDetails(gender);
                    times++;
                    dialog.dismiss();
                }

                else
                if(snapshot.exists() && times==0){

                    regUsername.setError("Username already exist");

                }



            }  //onDataChange method

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                //Toast to show error

                Toast.makeText(UserDetailsActivity.this, "Error occur while testing username", Toast.LENGTH_SHORT).show();

            }

        });  //addValue listener


    }  //username Test



//------------------------------------------------------------------------------------------------------


    //Login username Test

    public  void loginUsernameTest(String username , final Dialog dialog){

        times = 0;

        //String variable
        final String usernameLogin = username;

        //Firebase database instance and reference

        FirebaseDatabase firebaseDatabase;
        final DatabaseReference databaseReference2;

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference2=firebaseDatabase.getReference("Users");
        Query checkUser = databaseReference2.orderByChild("userName").equalTo(usernameLogin);


        //Testing the Username
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Condition to test username already existed

                if(!snapshot.exists()&& times==0 ){

                    loginUsername.setError("Invalid username");

                }

                else
                if(snapshot.exists() ){

                    dialog.dismiss();

                    //calling method to set userDetails

                    setAllUserDetails();

                    //After successfully testing username for logged in

                    Intent  intent = new Intent(UserDetailsActivity.this, UserHomeActivity.class);
                    intent.putExtra("connectType","Login");
                    startActivity(intent);
                    finish();

                }



            }  //onDataChange method

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                //Toast to show error

                Toast.makeText(UserDetailsActivity.this, "Error occur while testing username", Toast.LENGTH_SHORT).show();

            }

        });  //addValue listener



    }  //Login username test


    //Method to set the value in shared preference


    private void setAllUserDetails() {



        FirebaseDatabase firebaseDatabase;
        final DatabaseReference databaseReference2;

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference2=firebaseDatabase.getReference("Users/"+loginUsername.getText().toString());

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //getting value from firebase database

                String name = Objects.requireNonNull(snapshot.child("name").getValue()).toString();
                String language = Objects.requireNonNull(snapshot.child("language").getValue()).toString();
               // String language = snapshot.child("language").getValue().toString();

                //Setting value on shared preference
                final AutoLogin shared = new AutoLogin(UserDetailsActivity.this);
                shared.setUserName(loginUsername.getText().toString());
                shared.setName(name);
                shared.setLanguage(language);


                //Setting jokes and quote


                FirebaseDatabase firebaseDatabase;
                final DatabaseReference databaseReference2;

                firebaseDatabase=FirebaseDatabase.getInstance();
                databaseReference2=firebaseDatabase.getReference("imageUrls/"+shared.getLanguage());

                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        shared.setJokeUri(dataSnapshot.child("Joke").getValue(String.class));
                        shared.setQuoteUri(dataSnapshot.child("Quote").getValue(String.class));

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(UserDetailsActivity.this, "Unable to fetch data from firebase", Toast.LENGTH_SHORT).show();
                    }
                });




            }  //onDataChange method

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                //Toast to show error

                Toast.makeText(UserDetailsActivity.this, "Error occur while testing username", Toast.LENGTH_SHORT).show();

            }

        });  //addValue listener

    }


//------------------------------------------------------------------------------------------------------

    //Method which will upload user details on firebase

    public void uploadUserDetails(final String gender) {


        //String variable
        String username = regUsername.getText().toString();
        String language = "none";

        //Firebase database instance and reference

        FirebaseDatabase firebaseDatabase;
        final DatabaseReference databaseReference ,databaseReference2;

        //Getting instance and getting nodes
        //From FireBase

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users/"+regUsername.getText().toString());


        //Class object

        UserDetailHolder userDetailHolder = new UserDetailHolder(regName.getText().toString(),regUsername.getText().toString(),gender,language);

        //Setting value on shared preference

        final AutoLogin shared = new AutoLogin(this);
        shared.setUserName(regUsername.getText().toString());
        shared.setName(regName.getText().toString());
        shared.setLanguage(language);

        //Setting jokes and quote

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference2=firebaseDatabase.getReference("imageUrls/"+shared.getLanguage());

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                shared.setJokeUri(dataSnapshot.child("Joke").getValue(String.class));
                shared.setQuoteUri(dataSnapshot.child("Quote").getValue(String.class));

             }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(UserDetailsActivity.this, "Unable to fetch data from firebase", Toast.LENGTH_SHORT).show();
            }
        });



        //Setting value on database

        databaseReference.setValue(userDetailHolder);

        //After successfully te
        // sting username for logged in

        Intent  intent = new Intent(UserDetailsActivity.this, LanguageActivity.class);
        intent.putExtra("connectType","Registration");
        startActivity(intent);
        finish();

    }  //Upload user details


    //Method to set all user details for login



}  //class