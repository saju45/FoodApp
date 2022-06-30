package com.example.foodapp.Activity.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.Activity.Activity.PlaceOrderActivity;
import com.example.foodapp.Activity.Adapter.User.UserCartAdapter;
import com.example.foodapp.Activity.Model.FoodPostModel;
import com.example.foodapp.Activity.Model.OrderModel;
import com.example.foodapp.R;
import com.example.foodapp.databinding.FragmentCartBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.OptionalDouble;


public class CartFragment extends Fragment {

    FragmentCartBinding binding;
    DatabaseReference databaseReference;
    UserCartAdapter adapter;
    ArrayList<OrderModel> list;
    OrderModel model;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding=FragmentCartBinding.inflate(inflater, container, false);

         list=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Food");

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.carRecycler.setLayoutManager(layoutManager);
        binding.carRecycler.setHasFixedSize(true);

        adapter=new UserCartAdapter(getContext(),list);
        binding.carRecycler.setAdapter(adapter);


        databaseReference.child("userCart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    model=snapshot.getValue(OrderModel.class);
                    list.add(model);
                    binding.placeOrder.setVisibility(View.VISIBLE);
                    binding.simpleImg.setVisibility(View.GONE);
                    binding.text1.setVisibility(View.GONE);
                    binding.text2.setVisibility(View.GONE);
                    binding.text3.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),PlaceOrderActivity.class);
                 startActivity(intent);
            }
        });


        return binding.getRoot();
    }
}