package com.anvelopers.happymorning.showallluser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anvelopers.happymorning.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PostAdapter extends FirebaseRecyclerAdapter<PostModel, PostAdapter.PostViewHolder> {


    public PostAdapter(@NonNull FirebaseRecyclerOptions<PostModel> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull PostModel model) {

        holder.username.setText("Username : "+model.getUserName());
        holder.usergender.setText("Gender : "+model.getGender());


    }



    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post, parent, false);

        return new PostViewHolder(view);}


    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView username,usergender;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            username= itemView.findViewById(R.id.firebaseUsername);
            usergender = itemView.findViewById(R.id.firebaseGender);

        }
    }


}
