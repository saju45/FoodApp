package com.example.foodapp.Activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodapp.Activity.Fragment.Admin.AdminFavouriteFragment;
import com.example.foodapp.Activity.Fragment.Admin.AdminHomeFragment;
import com.example.foodapp.Activity.Fragment.Admin.AdminProfileFragment;
import com.example.foodapp.Activity.Fragment.Admin.CreateItemFragment;
import com.example.foodapp.Activity.Fragment.CartFragment;
import com.example.foodapp.Activity.Fragment.FavouriteFragment;
import com.example.foodapp.Activity.Fragment.HomeFragment;
import com.example.foodapp.Activity.Fragment.SearchFragment;
import com.example.foodapp.Activity.Fragment.SettingFragment;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityAdminHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminHomeActivity extends AppCompatActivity {

    ActivityAdminHomeBinding binding;
    String[] itemNme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new AdminHomeFragment()).commit();

        itemNme=getResources().getStringArray(R.array.spinner_item);

        binding.bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.amdin_home:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new AdminHomeFragment()).commit();

                        break;
                    case R.id.admin_fvt:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new AdminFavouriteFragment()).commit();

                        break;
                    case R.id.admin_profile:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new AdminProfileFragment()).commit();

                        break;
                    case R.id.create_item:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new CreateItemFragment()).commit();
                        break;



                }
                return true;
            }
        });

    }
}