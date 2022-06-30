package com.example.foodapp.Activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.foodapp.Activity.Model.FoodPostModel;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivitySingleItemBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleItemActivity extends AppCompatActivity {

    ActivitySingleItemBinding binding;
    String postedBy;
    String postId;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySingleItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        databaseReference= FirebaseDatabase.getInstance().getReference().child("Food");

        Intent intent=getIntent();
        postedBy=intent.getStringExtra("postedBy");
        postId=intent.getStringExtra("postId");


        binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SingleItemActivity.this,OrderActivity.class);
                intent.putExtra("postId",postId);
                startActivity(intent);
              //  startActivity(new Intent(SingleItemActivity.this,OrderActivity.class));
            }
        });


        databaseReference.child("Categories").child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    FoodPostModel model=snapshot.getValue(FoodPostModel.class);

                    Picasso.get().load(model.getPostImg()).placeholder(R.drawable.programmer).into(binding.imageview);
                    binding.name.setText(model.getFoodName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}