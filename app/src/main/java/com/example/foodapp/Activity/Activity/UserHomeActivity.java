package com.example.foodapp.Activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.MenuItem;
import android.view.View;

import com.example.foodapp.Activity.Fragment.CartFragment;
import com.example.foodapp.Activity.Fragment.FavouriteFragment;
import com.example.foodapp.Activity.Fragment.HomeFragment;
import com.example.foodapp.Activity.Fragment.SearchFragment;
import com.example.foodapp.Activity.Fragment.SettingFragment;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityUserHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserHomeActivity extends AppCompatActivity {


    ActivityUserHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment()).commit();

        binding.bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               switch (item.getItemId())
               {
                   case R.id.home:

                       getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment()).commit();

                       break;
                   case R.id.search:

                       getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new SearchFragment()).commit();

                       break;
                   case R.id.cart:

                       getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new CartFragment()).commit();

                       break;
                   case R.id.fvt:

                       getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FavouriteFragment()).commit();

                       break;

                   case R.id.setting:

                       getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new SettingFragment()).commit();

                       break;
               }
               return true;
           }
       });

    }
}