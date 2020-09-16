package com.anvelopers.happymorning.SliderView;  //Package

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.anvelopers.happymorning.Adapter.SliderAdapter;
import com.anvelopers.happymorning.R;
import com.anvelopers.happymorning.UserDetails.UserDetailsActivity;


public class OnboardingActivity extends AppCompatActivity {  //OnboardingActivity class


    //Instance

    private ViewPager slideViewPager;
    private LinearLayout dotsLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] dots;
    private Button backButton,nextButton,startButton;
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //OnCreate Method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);



        //Hooks

        slideViewPager = findViewById(R.id.description);
        dotsLayout = findViewById(R.id.dots);
        sliderAdapter = new SliderAdapter(this);
        backButton = findViewById(R.id.backButton);
        nextButton =findViewById(R.id.nextButton);
        startButton = findViewById(R.id.startButton);


        //Setting Adapter to View Pager
        slideViewPager.setAdapter(sliderAdapter);

        //Setting the position of addDotIndicator as 0

        addDotIndicator(0);

        //OnPageListener with slideViewPager

        slideViewPager.addOnPageChangeListener(viewListener);

        //setOnClickListener with next button

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //clicking on next button will increase the slide by 1

                slideViewPager.setCurrentItem(currentPage +1);

            }
        });

        //setOnClickListener with back button

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //clicking on next button will decrease the slide by 1

                slideViewPager.setCurrentItem(currentPage -1);

            }
        });


        //Start button click listener

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //will redirect with after splash screen
                Intent intent = new Intent(OnboardingActivity.this, UserDetailsActivity.class);
                startActivity(intent);
                finish();


            }
        });


    } //OnCreate

    //Method addDotIndicator

    private void addDotIndicator(int position) {

        dots = new TextView[3];  //Giving the size as 3

        dotsLayout.removeAllViews();  //Removing all views

        for(int i = 0 ;i<dots.length ;i++){  //for loop for slide changer

            dots [i] = new TextView(this);
            dots [i].setText(Html.fromHtml("&#8226"));
            dots [i].setTextSize(35);
            dots [i].setTextColor(getResources().getColor(R.color.dotsColor));

            dotsLayout.addView(dots[i]); //adding view of dots when slide change

        }  //for loop

        if(dots.length > 0){

            dots[position].setTextColor(getResources().getColor(R.color.WhiteColor));  //changing the color of dot

        } //if condition

    }  //method addDotIndicator



    //OnPageChangeListener

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotIndicator(position);  //Adding the dot

            currentPage = position;  //getting the position

            //If condition

            if(position==0)
            {
                /*
                if condition will zero then
                it will only show next button.

                 */

                nextButton.setEnabled(true);
                backButton.setEnabled(false);
                backButton.setVisibility(View.INVISIBLE);

                nextButton.setText("Next");
                backButton.setText("");

            }
            else if(position == dots.length-1){

                /*

                If it is the middle slide then both
                button will show back and next

                 */

                nextButton.setEnabled(false);
                backButton.setEnabled(true);
                backButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.INVISIBLE);

                nextButton.setText("");
                backButton.setText("Back");

            }

            else{

                /*

                If last position then it will
                show only back button

                 */

                nextButton.setEnabled(true);
                backButton.setEnabled(true);
                backButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);

                nextButton.setText("Next");
                backButton.setText("Back");

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };  //onPageChangeListener

}  //Class