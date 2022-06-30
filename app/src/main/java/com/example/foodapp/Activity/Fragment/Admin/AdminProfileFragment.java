package com.example.foodapp.Activity.Fragment.Admin;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodapp.R;
import com.example.foodapp.databinding.FragmentAdminProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AdminProfileFragment extends Fragment {


    FragmentAdminProfileBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseStorage storage;
    int IMAGE_REQUEST_CODE=101;

    Uri imageUri;

    public AdminProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAdminProfileBinding.inflate(inflater, container, false);


        databaseReference= FirebaseDatabase.getInstance().getReference().child("foodUser");
        storage=FirebaseStorage.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();


        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==IMAGE_REQUEST_CODE &&  resultCode==RESULT_OK && data!=null && data.getData()!=null )
        {
            imageUri=data.getData();
            binding.profileImage.setImageURI(imageUri);

            final StorageReference reference=storage.
                    getReference().child("profile_pic").child(FirebaseAuth.getInstance().getUid()+System.currentTimeMillis());

            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(getContext(), "profile uploaded", Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(uri.toString());

                            }
                        });
                    }else {
                        Toast.makeText(getContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}