package com.example.foodapp.Activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.Activity.Model.FoodUser;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivitySignUpaBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpaActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    ActivitySignUpaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Sign Up");
      //  getActionBar().setDisplayHomeAsUpEnabled(true);


        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference();

        binding.gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpaActivity.this,LoginActivity.class));
            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=binding.name.getText().toString();
                String email=binding.email.getText().toString();
                String phone=binding.phone.getText().toString();
                String password=binding.password.getText().toString();

                if (name.isEmpty())
                {
                    binding.name.setError("please enter your name");
                    binding.name.requestFocus();
                }else if (email.isEmpty())
                {
                    binding.email.setError("Please enter your email");
                    binding.email.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    binding.email.setError("Please enter your valid Email ");
                    binding.email.requestFocus();

                }if (phone.isEmpty())
                    {
                        binding.phone.setError("please enter your phone");
                        binding.phone.requestFocus();
                    }else if (password.isEmpty())
                    {
                        binding.password.setError("enter yor password");
                        binding.password.requestFocus();
                    }else if (password.length()<6)
                    {
                        binding.password.setError("please enter password 6 digit");
                        binding.password.requestFocus();
                    }else {

                    auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    if (task.isSuccessful())
                                    {
                                        String currentUser= task.getResult().getUser().getUid();
                                        FoodUser foodUser=new FoodUser(name,email,phone,password);
                                        reference.child("FoodUser").child(currentUser)
                                                        .setValue(foodUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful())
                                                        {
                                                            Toast.makeText(SignUpaActivity.this, "Registration is success", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(SignUpaActivity.this,LoginActivity.class));
                                                            finish();
                                                        }else {
                                                            Toast.makeText(SignUpaActivity.this, "registration failed", Toast.LENGTH_SHORT).show();
                                                        }


                                                    }
                                                });

                                    }

                                }
                            });

                    }
                }


        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}