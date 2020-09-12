package com.example.happymorning.Adapter;  //Package

//imported classes

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.example.happymorning.R;

public class SliderAdapter extends PagerAdapter {  //SliderAdapter Class

    //Instances

    Context context;
    LayoutInflater inflater;

    //Constructor of SliderAdapter

    public SliderAdapter(Context context){
        this.context=context;
    }

    // Array of int
    public int[] slideImages = {

            R.drawable.night,
            R.drawable.morning,
            R.drawable.lazy

    };

    // Array of String

    public String[] slideHeading = {

           "SLEEPLESS NIGHT",
            "MORNING",
            "LAZY DAY"

    };

    //Array of String

    public String[] showDescription={

            "You're working yourself till late at night, and sleeping crazy with deadlines - you're doing it for a reason.",
            "The way you start your morning sets the tone for the rest of the day. If you have an excellent start to the day, you’ll be in the perfect mood for the adventures and challenges ahead.",
            "What better way could there be than to start your morning with a really funny joke and with motivational quote? Our quotes and jokes will not only give you a good laugh but will also help you to start your day the right way."

    };

    @Override
    public int getCount() {
        return slideHeading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == (RelativeLayout) object;  //returning layout

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //getting layout Inflater for to get the DescriptionLayout view

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.descriptionlayout,container,false);

        //Hooks of image View and text View

        ImageView slideImageView = (ImageView)view.findViewById(R.id.descriptionImage);
        TextView slideDescriptionType = (TextView) view.findViewById(R.id.descriptionType);
        TextView slideDescription = (TextView) view.findViewById(R.id.descriptionTextView);

        //Setting image and text

        slideImageView.setImageResource(slideImages[position]);
        slideDescriptionType.setText(slideHeading[position]);
        slideDescription.setText(showDescription[position]);

        container.addView(view);

        return view;  //returning view
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);  //destroying layout

    }


}  //class
