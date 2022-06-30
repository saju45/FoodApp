package com.example.foodapp.Activity.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.Activity.Model.FoodPostModel;
import com.example.foodapp.Activity.Model.OrderModel;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityOrderBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class OrderActivity extends AppCompatActivity {

    ActivityOrderBinding binding;
    String postId;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    String time;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    int number=0;
    String itemSize,sideDish;

    FoodPostModel model;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        calendar= Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("hh:mm a");
        time=simpleDateFormat.format(calendar.getTime());


        Intent intent=getIntent();
        postId=intent.getStringExtra("postId");

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Food");

        binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number++;
                binding.foodCount.setText(number+"");
            }
        });

        binding.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (number>0)
                number--;
                binding.foodCount.setText(number+"");


            }
        });



        binding.smallText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemSize="small";
                binding.smallText.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });


        binding.mediumText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemSize="medium";
                binding.smallText.setBackgroundColor(getResources().getColor(R.color.green));

            }
        });

        binding.largeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSize="large";
                binding.smallText.setBackgroundColor(getResources().getColor(R.color.green));

            }
        });


        binding.cheeseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sideDish="cheese";
                binding.smallText.setBackgroundColor(getResources().getColor(R.color.green));

            }
        });

        binding.sosText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sideDish="Tomato sos";
                binding.smallText.setBackgroundColor(getResources().getColor(R.color.green));

            }
        });


        databaseReference.child("Categories").child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                     model=snapshot.getValue(FoodPostModel.class);

                    Picasso.get().load(model.getPostImg()).placeholder(R.drawable.programmer).into(binding.itemImage);
                    binding.itemName.setText(model.getFoodName());
                    binding.itemDescription.setText(model.getPostDescription());
                    binding.itemPrice.setText(model.getFoodPrice());
                    binding.price.setText(model.getFoodPrice());
                    binding.price2.setText(model.getFoodPrice());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.fvtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String image=model.getPostImg();
                String itemName=binding.itemName.getText().toString();
                String itemPrice=binding.itemPrice.getText().toString();
                String itemDescription=binding.itemDescription.getText().toString();
                String count=binding.foodCount.getText().toString();


                FoodPostModel model=new FoodPostModel(itemName,postId,image,firebaseUser.getUid(),itemDescription,itemPrice,"",time);

                databaseReference.child("userFavourite").child(firebaseUser.getUid()).child(postId).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {

                            Toast.makeText(OrderActivity.this, "favourite food  added", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(OrderActivity.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });


                binding.fvtBtn.setBackgroundColor(getResources().getColor(R.color.red));

            }
        });



        binding.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String image=model.getPostImg();
                String itemName=binding.itemName.getText().toString();
                String itemPrice=binding.itemPrice.getText().toString();
                String itemDescription=binding.itemDescription.getText().toString();
                String count=binding.foodCount.getText().toString();

                int countInt=Integer.parseInt(count);
                int price=Integer.parseInt(itemPrice);

                OrderModel orderModel=new OrderModel(countInt,price,postId,itemSize,sideDish,itemName,itemDescription,image);
               // FoodPostModel model=new FoodPostModel(itemName,postId,image,firebaseUser.getUid(),itemDescription,itemPrice,"",time);

                databaseReference.child("userCart").setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(OrderActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(OrderActivity.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }
}