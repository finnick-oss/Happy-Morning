package com.anvelopers.happymorning.HomeActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anvelopers.happymorning.userImageViews.UserUploadImages;
import com.bumptech.glide.Glide;
import com.anvelopers.happymorning.ConnectivityCheck.InternetConnectivityCheck;
import com.anvelopers.happymorning.R;
import com.anvelopers.happymorning.SharedPreference.AutoLogin;
import com.anvelopers.happymorning.UserDetails.UserDetailsActivity;
import com.anvelopers.happymorning.language.LanguageActivity;
import com.anvelopers.happymorning.showallluser.PostListActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserHomeActivity extends AppCompatActivity {

    //Instances

    private EditText textFeedback;
    private ImageView backButton, uploadUserImageView, quote, joke,addImage;
    private TextView userName, greetingMsg, userHomeName,userUploadContent;
    private LinearLayout option;
    private String connectType;
    private Button termsAndCondition, languageOption, logoutUser, userFeedback, submitRatings, submitUserImage,showUserShare,userJokes,userQuotes;
    private RatingBar bar;
    private Float totalRatings;
    private int totalUsers;
    private float ratingValue;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference, reference2;
    private Uri fileImageUri, imageUri,resultUri;
    private StorageReference mStorageRef;
    private RadioGroup imageType;


    //Sharing images button

    ImageView shareImageQuote,shareImageJoke;


    //admob
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        //Hooks
        userHomeName = findViewById(R.id.welcomeUserName);
        option = findViewById(R.id.optionbutton);
        showUserShare = findViewById(R.id.showUser);
        userUploadContent =findViewById(R.id.shareAndUploadJokesAndQuote);
        quote = findViewById(R.id.quoteImage);
        joke = findViewById(R.id.jokeImage);
        addImage = findViewById( R.id.uploadButton);
        userQuotes = findViewById(R.id.userQuotes);
        userJokes = findViewById(R.id.userJokes);


        //ShareImage

        shareImageQuote=findViewById(R.id.shareImage);
        shareImageJoke = findViewById(R.id.shareImage2);


        //Shared preference object for auto login

        final AutoLogin shared = new AutoLogin(UserHomeActivity.this);


        //Connectivity class object

        final InternetConnectivityCheck connectivityCheck = new InternetConnectivityCheck();



        //Testing user is admin or not

        if(shared.getUserName().equals("Anurag@admin")){

            showUserShare.setVisibility(View.VISIBLE);

        }


        //setting on click listener

        showUserShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connectivityCheck.isNetworkAvailable(UserHomeActivity.this)) {
                    Intent intent = new Intent(UserHomeActivity.this, PostListActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(UserHomeActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }

            }
        });


        userQuotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(connectivityCheck.isNetworkAvailable(UserHomeActivity.this)) {

                    Intent intent = new Intent(UserHomeActivity.this,UserUploadImages.class);
                    intent.putExtra("Type","Quote");
                    startActivity(intent);
                    finish();
                }
                else {

                    Toast.makeText(UserHomeActivity.this, "Network error", Toast.LENGTH_SHORT).show();

                }

            }
        });




        userJokes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(connectivityCheck.isNetworkAvailable(UserHomeActivity.this)) {

                    Intent intent = new Intent(UserHomeActivity.this,UserUploadImages.class);
                    intent.putExtra("Type","Joke");
                    startActivity(intent);
                    finish();
                }
                else {

                    Toast.makeText(UserHomeActivity.this, "Network error", Toast.LENGTH_SHORT).show();

                }

            }
        });




        if(connectivityCheck.isNetworkAvailable(UserHomeActivity.this)) {

            FirebaseDatabase firebaseDatabase;
            final DatabaseReference databaseReference2;

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference2 = firebaseDatabase.getReference("imageUrls/" + shared.getLanguage());

            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    shared.setJokeUri(dataSnapshot.child("Joke").getValue(String.class));
                    shared.setQuoteUri(dataSnapshot.child("Quote").getValue(String.class));

                    quote = findViewById(R.id.quoteImage);

                    Glide
                            .with(UserHomeActivity.this)
                            .load(Uri.parse(shared.getQuoteUri()))
                            .into(quote);


                    joke = findViewById(R.id.jokeImage);
                    Glide
                            .with(UserHomeActivity.this)
                            .load(Uri.parse(shared.getJokeUri()))
                            .into(joke);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Toast.makeText(UserHomeActivity.this, "Unable to fetch data from firebase", Toast.LENGTH_SHORT).show();
                }


            });
        }



        if(shared.getJokeUri() !=null && shared.getQuoteUri()!=null){


            quote = findViewById(R.id.quoteImage);

            Glide
                    .with(UserHomeActivity.this)
                    .load(Uri.parse(shared.getQuoteUri()))
                    .into(quote);


            joke = findViewById(R.id.jokeImage);
            Glide
                    .with(UserHomeActivity.this)
                    .load(Uri.parse(shared.getJokeUri()))
                    .into(joke);



        }

        else{

            Toast.makeText(this, " This "+shared.getJokeUri(), Toast.LENGTH_SHORT).show();

        }



        //Setting text
        userHomeName.setText("Hi ! "+shared.getUserName());



        //Getting the value from connect type which is intent

        connectType = getIntent().getStringExtra("connectType");

        //Showing dialog box according to connect type

        if(connectType.equals("ShareType") || connectType.equals("Login") ||connectType.equals("UserShare")  || connectType.equals("Admin")){

            //Showing welcome user dialog box

            greetingUser();


        }

        //OnClick button option will show

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

         //Calling option bar

           showOptionBar();

            }
        });




        //Admob


        mAdView = findViewById(R.id.adView);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        if(shared.getUserName().equals("Anurag@admin")) {

            mAdView.setVisibility(View.GONE);

        }else {

            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);


        }



        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showImageUpload();

            }
        });




        //Clicklistener for share image

        shareImageQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImageToWhatsApp(shared.getQuoteUri());
            }
        });

        shareImageJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareImageToWhatsApp(shared.getJokeUri());

            }
        });



    }  //onCreate


    //method for showing image upload by user

    private void showImageUpload() {

        //Showing dialog

        final Dialog dialog = new Dialog(UserHomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.useruploadimage);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        //Hooks
    //    backButton =  dialog.findViewById(R.id.backButton);

        //User will click on back button
/*        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });*/


        submitUserImage =  dialog.findViewById(R.id.submitUserQuoteandJokes);
        uploadUserImageView =  dialog.findViewById(R.id.uploadImage);
        imageType =  dialog.findViewById(R.id.typeDetails);


        userUploadContent = dialog.findViewById(R.id.shareAndUploadJokesAndQuote);


        //getting the url from upload image
        uploadUserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calling choose picture

                choosePicture();

            }
        });



        userUploadContent.setText("Upload Jokes and Quotes");

        // Uncheck or reset the radio buttons initially

        imageType.clearCheck();


        // Add the Listener to the RadioGroup
        imageType.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId) {
                        // Get the selected Radio Button
                        RadioButton
                                radioButton
                                = (RadioButton) group
                                .findViewById(checkedId);
                    }
                });




        //Submit image user
        submitUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking internet Connectivity

                InternetConnectivityCheck connectivityCheck = new InternetConnectivityCheck();

                if (connectivityCheck.isNetworkAvailable(UserHomeActivity.this)) {

                    // When submit button is clicked,
                    // Ge the Radio Button which is set
                    // If no Radio Button is set, -1 will be returned

                    int selectedId = imageType.getCheckedRadioButtonId();

                    if (selectedId == -1) {
                        Toast.makeText(UserHomeActivity.this,
                                "Please select the type",
                                Toast.LENGTH_SHORT)
                                .show();
                    } else if (imageUri == null) {

                        Toast.makeText(UserHomeActivity.this,
                                "Image would not be empty",
                                Toast.LENGTH_SHORT)
                                .show();

                    } else {
                        RadioButton radioButton
                                = (RadioButton) imageType
                                .findViewById(selectedId);

                        //Calling method to upload image

                        updateImage(resultUri, radioButton.getText().toString(),dialog);

                    }
                }
                else{

                    Toast.makeText(UserHomeActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }


    //-------------------------------------------------------------------------------------------

    //Method for choosing the picture from gallery


    public void choosePicture() {

        Intent intent = new Intent();
        intent.setType("image/*");  //inserting all images inside this Image folder
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    } //Choose Picture



    //--------------------------------------------------------------------------------------------

//When User will inside the gallery folder

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1  && resultCode == RESULT_OK && data != null && data.getData() != null) {

            //Getting the uri from gallery

            fileImageUri = data.getData();
            imageUri = fileImageUri;
            CropImage.activity(fileImageUri)  //cropping the image

                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setFixAspectRatio(true)
                    .setAspectRatio(1,1)
                    .start(this);



        }


        //After image will crop again taking the image uri

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                 resultUri = result.getUri();

                uploadUserImageView.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "Error While Getting uri", Toast.LENGTH_SHORT).show();
            }
        }

    }  //method



//--------------------------------------------------------------------------------------------------

    //Update Image

    private  void updateImage(Uri imageUploadUri , final String type, Dialog dialog) {


        //Shared preference object for auto login

        final AutoLogin shared = new AutoLogin(UserHomeActivity.this);

        Toast.makeText(this, "Loading Please wait", Toast.LENGTH_SHORT).show();


        //getting time

        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        //Firebase Storage Reference


        rootNode = FirebaseDatabase.getInstance();
        if(shared.getUserName().contains("Anurag@admin")) {

            reference2 = rootNode.getReference("imageUrls/"+shared.getLanguage());
            mStorageRef = FirebaseStorage.getInstance().getReference("HappyMorning/Admin/"+shared.getLanguage());

            final StorageReference riversRef = mStorageRef.child(type);

            riversRef.putFile(imageUploadUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            mStorageRef.child(type).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    //Setting the url to database

                                    reference2.child(type).setValue(uri.toString());

                                    Toast.makeText(UserHomeActivity.this, "Shared Successfully", Toast.LENGTH_SHORT).show();

                                    uploadUserImageView.setImageDrawable(getResources().getDrawable(R.drawable.uploadimage));

                                }

                            });
                        }

                    });


        }else{


            //getting user current to set image

            Calendar cal = Calendar.getInstance();
            Date dateType=cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            final String formattedDate=dateFormat.format(dateType);

            reference2 = rootNode.getReference("imageUrls/userUrl/"+shared.getLanguage()+"/"+type+"/"+date+"+"+formattedDate);

            mStorageRef = FirebaseStorage.getInstance().getReference("HappyMorning/Users/"+shared.getLanguage()+"/"+date+"+"+formattedDate);

            final StorageReference riversRef = mStorageRef.child("url");

            riversRef.putFile(imageUploadUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            mStorageRef.child("url").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    //Setting the url to database

                                    reference2.child("url").setValue(uri.toString());
                                    reference2.child("name").setValue(shared.getName());
                                    reference2.child("date").setValue(date+"+"+formattedDate);
                                    Toast.makeText(UserHomeActivity.this, "Shared Successfully", Toast.LENGTH_SHORT).show();


                                    uploadUserImageView.setImageDrawable(getResources().getDrawable(R.drawable.uploadimage));

                                }

                            });
                        }

                    });


        }


        imageUri = null;
        dialog.dismiss();

    }  //UpdateImage


//------------------------------------------------------------------------------------------------------



    //Method for greeting the user by showing dialog box

    public void greetingUser() {


        //Showing dialog

        final Dialog dialog = new Dialog(UserHomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.greetinguser);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        //Shared preference object for auto login

        final AutoLogin shared = new AutoLogin(this);


        //Hooks

        backButton =  dialog.findViewById(R.id.backButton);
        userName = dialog.findViewById(R.id.userNamegreeting);
        greetingMsg = dialog.findViewById(R.id.greetings);

        //Setting greeting


        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate=dateFormat.format(date);

        //Splitting the date

        String[] timeInString = formattedDate.split(":");


        int time = Integer.parseInt(timeInString[0]);

        if(time>=0 && time<12){

            greetingMsg.setText("Good Morning");

        }
        else if(time>=12 && time<17){

            greetingMsg.setText("Good Afternoon");

        }
        else{

            greetingMsg.setText("Good evening");

        }


        //Setting user name

        userName.setText(shared.getUserName());

        //User will click on back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }  //greetingUser



//------------------------------------------------------------------------------------------------------


    //Method to show show menu bar

    private void  showOptionBar(){


        //Shared preferences object of AutoLogin

        final AutoLogin autoLogin = new AutoLogin(UserHomeActivity.this);

        //Showing dialog

        final Dialog dialog = new Dialog(UserHomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.showoption);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        //Hooks

        backButton =  dialog.findViewById(R.id.backButton);
        termsAndCondition =dialog. findViewById(R.id.terms);
        languageOption = dialog.findViewById(R.id.changeLanguage);
        logoutUser = dialog.findViewById(R.id.userLogout);

        userFeedback = dialog.findViewById(R.id.feedback);


        final InternetConnectivityCheck connectivityCheck = new InternetConnectivityCheck();

        //User will click on back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //dialog to show terms and condition

        termsAndCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calling dialog method

                showTermsAndCondition(dialog);

            }
        });

        //Logout the user when user will click on logout button
        logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             autoLogin.removeUser(UserHomeActivity.this);
             Intent intent = new Intent(UserHomeActivity.this,UserDetailsActivity.class);
             startActivity(intent);
             Toast.makeText(UserHomeActivity.this, "Logout SuccessFully", Toast.LENGTH_SHORT).show();
             finish();

            }
        });

        //on clicking language change button user will redirect on language Activity

        languageOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserHomeActivity.this, LanguageActivity.class);
                intent.putExtra("connectType","Home");
                startActivity(intent);

            }
        });


        //For feedback

        userFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(connectivityCheck.isNetworkAvailable(UserHomeActivity.this)) {

                    //dismissing the dialog and showing feedback dialog

                    dialog.dismiss();
                    showFeedbackDialog();
                }
                else{

                    Toast.makeText(UserHomeActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }  //method to show option bar



    public void showTermsAndCondition(Dialog dialogMethod){

        //dismissing the previous dialog

        dialogMethod.dismiss();

        //Showing dialog

        final Dialog dialog = new Dialog(UserHomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tearmandcondition);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        //Hooks
        backButton =  dialog.findViewById(R.id.backButton);

        //User will click on back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



    }



    //Method to show feedback activity

    private void showFeedbackDialog(){


        //Shared preferences object of AutoLogin

        final AutoLogin autoLogin = new AutoLogin(UserHomeActivity.this);

        //Showing dialog

        final Dialog dialog = new Dialog(UserHomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.userfeedback);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        //getting time

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        rootNode = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("feedback");
        reference2 = rootNode.getReference("feedback/UsersFeedback/"+date+"/"+autoLogin.getUserName());

        //Hooks

        backButton =  dialog.findViewById(R.id.backButton);
        bar=dialog.findViewById(R.id.rating_bar);
        submitRatings =dialog.findViewById(R.id.submitRating);
        textFeedback = dialog.findViewById(R.id.takingUserFeedback);

        //User will click on back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        //Taking rating bar value

        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float value, boolean b) {
                ratingValue=value;
            }
        });



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalRatings = Float.parseFloat(dataSnapshot.child("Total_ratings").getValue(String.class));
                totalUsers = Integer.parseInt(dataSnapshot.child("Total_users").getValue(String.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserHomeActivity.this, "The read failed: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });


        final InternetConnectivityCheck connectivityCheck =new InternetConnectivityCheck();

        //submit ratings to submit user ratings and feedback

        submitRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(connectivityCheck.isNetworkAvailable(UserHomeActivity.this)) {
                    if (ratingValue == 0.0) {

                        Toast.makeText(UserHomeActivity.this, "Enter Ratings", Toast.LENGTH_SHORT).show();
                    } else {
                        UploadRatings(dialog);
                    }

                }else{

                    Toast.makeText(UserHomeActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }  //method



    //Method to upload ratings

    private void UploadRatings(Dialog dialog) {


        //Shared preferences object of AutoLogin

        final AutoLogin autoLogin = new AutoLogin(UserHomeActivity.this);

        //getting user name from auto login shared preferences

        String name = autoLogin.getUserName();

        //Adding value before uploading to get final ratings

        totalRatings =totalRatings+ratingValue;
        totalUsers=totalUsers+1;


        //Setting date with time in firebase


        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate=dateFormat.format(date);




        //feedback testing is remaining

        String Feedback=textFeedback.getText().toString();


        reference.child("Total_ratings").setValue(Float.toString(totalRatings));
        reference.child("Total_users").setValue(Integer.toString(totalUsers));

        if(Feedback.length()>=3){
            reference2.child(formattedDate).setValue(Feedback);
            Toast.makeText(this, "Thanks for giving feedback "+ name, Toast.LENGTH_SHORT).show();
        }
        else{

            Toast.makeText(this, "Too short", Toast.LENGTH_SHORT).show();
        }


        //dismissing the dialog
        dialog.dismiss();

    }  //uploading ratings




    //Share Image to whatsApp

    public void shareImageToWhatsApp(String url) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(intent, "send"));

        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "What's App not installed", Toast.LENGTH_SHORT).show();
        }

    }



}  //class