package com.example.foodapp.Activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Login");
        auth=FirebaseAuth.getInstance();

        binding.gotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,UserHomeActivity.class));
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=binding.email.getText().toString();
                String password=binding.password.getText().toString();


                if (email.isEmpty())
                {
                    binding.email.setError("please enter your email");
                    binding.email.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    binding.email.setError("please enter your valid email");
                    binding.email.requestFocus();
                }else if (password.isEmpty())
                {
                    binding.password.setError("Enter your password");
                    binding.password.requestFocus();
                }else if (password.length()<6)
                {
                    binding.password.setError("password length should be 6");
                    binding.password.requestFocus();
                }else {



                    if (email.equals("admin@gmail.com") && password.equals("saju01"))
                    {
                        startActivity(new Intent(LoginActivity.this,AdminHomeActivity.class));
                        finish();
                    }


                    auth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(LoginActivity.this, "Login is successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(LoginActivity.this,UserHomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(LoginActivity.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }


            }
        });



    }
}