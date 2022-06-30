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
import com.example.foodapp.databinding.ActivityPlaceOrderBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class PlaceOrderActivity extends AppCompatActivity {

    ActivityPlaceOrderBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    OrderModel model;

    String time;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPlaceOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        calendar= Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("hh:mm a");
        time=simpleDateFormat.format(calendar.getTime());

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Food");
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();


        databaseReference.child("userCart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                  model =snapshot.getValue(OrderModel.class);
                    binding.name.setText(model.getName());
                    binding.foodPrice.setText(model.getPrice()+"");
                    binding.price.setText(model.getPrice()+"");
                    binding.deliveryPrice.setText("0");
                    binding.totalPrice.setText("00");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.paceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String price=binding.price.getText().toString();
                String address=binding.addressEt.getText().toString();
                String delivery=binding.deliveryPrice.getText().toString();
                String totalPrice=binding.totalPrice.getText().toString();


                int itemPrice= Integer.parseInt(price);
                int deliveryPrice=Integer.parseInt(delivery);
                int  total=Integer.parseInt(totalPrice);
                OrderModel orderModel= new OrderModel(model.getItemCount(),itemPrice,model.getPostId(),model.getItemSize(),model.getSideDish(),model.getName(),model.getDescription(),model.getImage());
                orderModel.setAddress(address);
                orderModel.setDelivery(deliveryPrice);
                orderModel.setTotal(total);
                orderModel.setTime(time);


                databaseReference.child("Order")
                        .child(firebaseUser.getUid())
                        .child(model.getPostId())
                        .setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful())
                                {
                                    Toast.makeText(PlaceOrderActivity.this, "Order is success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PlaceOrderActivity.this,UserHomeActivity.class));
                                }
                            }
                        });

            }
        });
    }
}