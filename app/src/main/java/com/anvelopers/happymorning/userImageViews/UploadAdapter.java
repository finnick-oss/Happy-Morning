package com.anvelopers.happymorning.userImageViews;


import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.anvelopers.happymorning.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;


public class UploadAdapter extends FirebaseRecyclerAdapter<UploadModel, UploadAdapter.PostViewHolder>  {


    public UploadAdapter(@NonNull FirebaseRecyclerOptions<UploadModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder  (@NonNull final PostViewHolder holder, int position, @NonNull final UploadModel model) {

       final String url = model.getUrl();

        holder.username.setText("-"+model.getName());
        Picasso.get().load(model.getUrl()).into(holder.uploadedImage);

        holder.shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String whatsAppMessage = "Happy Morning";

                //You can read the image from external drove too

                   Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT,model.getUrl());
                    intent.setType("text/plain");
                    intent.setPackage("com.whatsapp");
                    view.getContext().startActivity(Intent.createChooser(intent, "send"));

                try {
                    view.getContext().startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(view.getContext(), "What's App not installed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @NonNull
    @Override
    public UploadAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userimagecard, parent, false);

        return new PostViewHolder(view);}



    class PostViewHolder extends RecyclerView.ViewHolder  {


        TextView username;
        ImageView uploadedImage;
        ImageView shareImage;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            username= itemView.findViewById(R.id.fetchingUserName);
            uploadedImage = itemView.findViewById(R.id.setUlpoadedImage);
            shareImage = itemView.findViewById(R.id.shareImage);

            
        }



    }



}