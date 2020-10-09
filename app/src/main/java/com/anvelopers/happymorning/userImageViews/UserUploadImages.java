package com.anvelopers.happymorning.userImageViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.anvelopers.happymorning.ConnectivityCheck.InternetConnectivityCheck;
import com.anvelopers.happymorning.HomeActivity.UserHomeActivity;
import com.anvelopers.happymorning.R;
import com.anvelopers.happymorning.SharedPreference.AutoLogin;
import com.anvelopers.happymorning.showallluser.PostAdapter;
import com.anvelopers.happymorning.showallluser.PostListActivity;
import com.anvelopers.happymorning.showallluser.PostModel;
import com.efaso.admob_advanced_native_recyvlerview.AdmobNativeAdAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class UserUploadImages extends AppCompatActivity {


    private RecyclerView recyclerView;
    private UploadAdapter adapter;
    private ImageView back;
    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_upload_images);


        AutoLogin autoLogin = new AutoLogin(UserUploadImages.this);

        Intent intent = getIntent();
        String buttontype=intent.getStringExtra("Type");

        recyclerView=findViewById(R.id.userUploadedRecycler);

        back = findViewById(R.id.backImage);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        //Admob


        mAdView = findViewById(R.id.adView4);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        FirebaseRecyclerOptions<UploadModel> options =
                new FirebaseRecyclerOptions.Builder<UploadModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("imageUrls/userUrl/"+autoLogin.getLanguage()+"/"+buttontype), UploadModel.class)
                        .build();


        adapter = new UploadAdapter(options);

        AdmobNativeAdAdapter admobNativeAdAdapter=AdmobNativeAdAdapter.Builder
                .with(
                        "ca-app-pub-1290189764249392/9419624677",//Create a native ad id from admob console
                        adapter,//The adapter you would normally set to your recyClerView
                        "small"
                )
                .adItemIterval(4)//native ad repeating interval in the recyclerview
                .build();

        recyclerView.setAdapter(admobNativeAdAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserUploadImages.this, UserHomeActivity.class);
                intent.putExtra("connectType","UserShare");
                startActivity(intent);

            }
        });




    }  //onCreate



    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    }


