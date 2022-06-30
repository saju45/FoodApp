package com.example.foodapp.Activity.Fragment.Admin;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.foodapp.Activity.Model.FoodPostModel;
import com.example.foodapp.R;
import com.example.foodapp.databinding.FragmentCreateItemBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Locale;


public class CreateItemFragment extends Fragment {

    FragmentCreateItemBinding binding;
    String[] createItem;

    DatabaseReference databaseReference;
    FirebaseDatabase database;
    StorageReference storageReference;
    FirebaseAuth auth;
    ProgressDialog dialog;
    String postedBy;
    int IMAGE_REQUEST_CODE = 101;
    private Uri imageUri;

    String time;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    FoodPostModel model;
    public CreateItemFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding=FragmentCreateItemBinding.inflate(inflater, container, false);



         createItem=getResources().getStringArray(R.array.spinner_item);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),R.layout.simple_adapter,R.id.simple_textView,createItem);
        binding.spinner.setAdapter(adapter);


      //  databaseReference = FirebaseDatabase.getInstance().getReference().child("Food");
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        dialog=new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Post Uploading");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        storageReference= FirebaseStorage.getInstance().getReference("posts");


        calendar= Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("hh:mm a");
        time=simpleDateFormat.format(calendar.getTime());


        binding.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.spinner.getSelectedItem().toString().equals("Special Offer"))
                {
                    binding.newPrice.setVisibility(View.VISIBLE);
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Food").child("SpecialOffer");
                }else if (binding.spinner.getSelectedItem().toString().equals("Categories"))
                {
                    //databaseReference.child("Categories");
                    binding.newPrice.setVisibility(View.GONE);
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Food").child("Categories");

                }else if (binding.spinner.getSelectedItem().toString().equals("Popular"))
                {
                    //databaseReference.child("Popular");
                    binding.newPrice.setVisibility(View.GONE);
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Food").child("Popular");

                }
            }
        });



        binding.galleryimgId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        });



        binding.postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String postType="";
                String postText=binding.addPostDescription.getText().toString();
                if(postText.isEmpty() && imageUri==null){
                    Toast.makeText(getContext(), "At Least Image Or Text Need To Add", Toast.LENGTH_SHORT).show();
                }else{

                    saveData();
                }

            }
        });


         return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.postImg.setVisibility(View.VISIBLE);
            binding.postImg.setImageURI(imageUri);

            binding.postBtn.setEnabled(true);
            binding.postBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_gradiant));
            binding.postBtn.setTextColor(getContext().getResources().getColor(R.color.white));

        }
    }
    public String getFileExtension(Uri imageUri){
        ContentResolver contentResolver=getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    public void saveData(){

        String postId=databaseReference.push().getKey();
        //String postMessage=messsage.isEmpty()?messsage:"";
        String foodName=binding.foodName.getText().toString();
        String postMessage=binding.addPostDescription.getText().toString();
        String postPrice=binding.oldPrice.getText().toString();
        String newPrice=binding.newPrice.getText().toString();


        StorageReference ref=storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getActivity(), "successfully post", Toast.LENGTH_LONG).show();

                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    Uri loadUri=uriTask.getResult();


                    if (binding.spinner.getSelectedItem().toString().equals("Special Offer"))
                    {
                        model=new FoodPostModel(foodName,postId,loadUri.toString(),postedBy,postMessage,postPrice,newPrice,time);

                    }else if (binding.spinner.getSelectedItem().toString().equals("Categories"))
                    {
                        model=new FoodPostModel(foodName,postId,loadUri.toString(),postedBy,postMessage,postPrice,"",time);
                    }else if (binding.spinner.getSelectedItem().toString().equals("Popular"))
                    {
                         model=new FoodPostModel(foodName,postId,loadUri.toString(),postedBy,postMessage,postPrice,"",time);
                    }


                    databaseReference.child(postId).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Post Upload Successful.", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(getContext(), "Post Upload Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }


    }
