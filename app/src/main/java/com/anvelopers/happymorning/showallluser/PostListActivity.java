package com.anvelopers.happymorning.showallluser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anvelopers.happymorning.HomeActivity.UserHomeActivity;
import com.anvelopers.happymorning.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class PostListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        recyclerView=findViewById(R.id.recycler);

        back = findViewById(R.id.backButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerOptions<PostModel> options =
                new FirebaseRecyclerOptions.Builder<PostModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), PostModel.class)
                        .build();


        adapter = new PostAdapter(options);

        recyclerView.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PostListActivity.this, UserHomeActivity.class);
                intent.putExtra("connectType","Admin");
                startActivity(intent);
            }
        });

    }


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