package com.example.foodapp.Activity.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.Activity.Adapter.User.UserCartAdapter;
import com.example.foodapp.Activity.Adapter.User.UserFvtAdapter;
import com.example.foodapp.Activity.Model.FoodPostModel;
import com.example.foodapp.R;
import com.example.foodapp.databinding.FragmentFavouriteBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FavouriteFragment extends Fragment {


    FragmentFavouriteBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ArrayList<FoodPostModel> list;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentFavouriteBinding.inflate(inflater, container, false);


        list=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Food");
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();


        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.fvtRecycler.setLayoutManager(layoutManager);
        binding.fvtRecycler.setHasFixedSize(true);


        UserFvtAdapter adapter=new UserFvtAdapter(getContext(),list);
        binding.fvtRecycler.setAdapter(adapter);


        databaseReference.child("userFavourite").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot snapshot1: snapshot.getChildren())
                {
                    FoodPostModel model=snapshot1.getValue(FoodPostModel.class);
                    list.add(model);
                    binding.image.setVisibility(View.GONE);
                    binding.text1.setVisibility(View.GONE);
                    binding.text2.setVisibility(View.GONE);
                    binding.text3.setVisibility(View.GONE);
                    binding.text4.setVisibility(View.GONE);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }
}