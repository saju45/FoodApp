package com.example.foodapp.Activity.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.Activity.Activity.MainActivity;
import com.example.foodapp.Activity.Activity.OrderDetailsActivity;
import com.example.foodapp.Activity.Model.FoodPostModel;
import com.example.foodapp.Activity.Model.FoodUser;
import com.example.foodapp.R;
import com.example.foodapp.databinding.FragmentSettingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SettingFragment extends Fragment {


    FragmentSettingBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding=FragmentSettingBinding.inflate(inflater, container, false);

         databaseReference= FirebaseDatabase.getInstance().getReference().child("FoodUser");
         firebaseAuth=FirebaseAuth.getInstance();
         firebaseUser=firebaseAuth.getCurrentUser();



         
         databaseReference.child(firebaseUser.getUid())
                         .addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot) {

                                 if (snapshot.exists())
                                 {
                                     FoodUser model=snapshot.getValue(FoodUser.class);
                                     binding.name.setText(model.getName());
                                     binding.email.setText(model.getName());

                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {

                             }
                         });

         binding.myOrders.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 startActivity(new Intent(getContext(), OrderDetailsActivity.class));
             }
         });

         binding.logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 firebaseAuth.signOut();
                 startActivity(new Intent(getContext(), MainActivity.class));
             }
         });



         return binding.getRoot();
    }
}