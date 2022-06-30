package com.example.foodapp.Activity.Fragment.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.Activity.Adapter.Admin.AdminCategories;
import com.example.foodapp.Activity.Adapter.Admin.AdminOfferAdapter;
import com.example.foodapp.Activity.Adapter.Admin.AdminPopularAdapter;
import com.example.foodapp.Activity.Model.FoodPostModel;
import com.example.foodapp.databinding.FragmentAdminHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminHomeFragment extends Fragment {

     FragmentAdminHomeBinding binding;
    ArrayList<FoodPostModel> categoriesList;
    ArrayList<FoodPostModel> offerList;
    ArrayList<FoodPostModel> popularList;


    FirebaseAuth firebaseAuth;
     DatabaseReference reference;
     FirebaseUser firebaseUser;

     AdminCategories adminCategories;
     AdminOfferAdapter adminOfferAdapter;
     AdminPopularAdapter adminPopularAdapter;

    LinearLayoutManager layoutManager;
    public AdminHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentAdminHomeBinding.inflate(inflater, container, false);


        categoriesList=new ArrayList<>();
        offerList=new ArrayList<>();
        popularList=new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("Food");

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        adminCategories=new AdminCategories(getContext(),categoriesList);
        adminOfferAdapter=new AdminOfferAdapter(getContext(),offerList);
        adminPopularAdapter=new AdminPopularAdapter(getContext(),popularList);

        layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);


        binding.categoriesRV.setLayoutManager(layoutManager);
        binding.categoriesRV.setHasFixedSize(true);
        binding.categoriesRV.setAdapter(adminCategories);

        LinearLayoutManager layoutManager1=new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        binding.specialOfferRv.setLayoutManager(layoutManager1);
        binding.specialOfferRv.setHasFixedSize(true);
        binding.specialOfferRv.setAdapter(adminOfferAdapter);


        LinearLayoutManager layoutManager2=new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(RecyclerView.VERTICAL);
        binding.popularRv.setLayoutManager(layoutManager2);
        binding.popularRv.setHasFixedSize(true);
        binding.popularRv.setAdapter(adminPopularAdapter);


        reference.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1: snapshot.getChildren())
                {
                    FoodPostModel model=snapshot1.getValue(FoodPostModel.class);
                    categoriesList.add(model);
                }
                adminCategories.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("SpecialOffer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1: snapshot.getChildren())
                {
                    FoodPostModel model=snapshot1.getValue(FoodPostModel.class);
                    offerList.add(model);
                }
                adminOfferAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference.child("Popular").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1: snapshot.getChildren())
                {
                    FoodPostModel model=snapshot1.getValue(FoodPostModel.class);
                    popularList.add(model);
                }
                adminOfferAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return binding.getRoot();
    }
}