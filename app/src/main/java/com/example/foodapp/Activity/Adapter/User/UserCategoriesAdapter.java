package com.example.foodapp.Activity.Adapter.User;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activity.Activity.SingleItemActivity;
import com.example.foodapp.Activity.Model.FoodPostModel;
import com.example.foodapp.R;
import com.example.foodapp.databinding.SimpleCategoriesBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserCategoriesAdapter extends RecyclerView.Adapter<UserCategoriesAdapter.viewHolder> {

    Context context;
    ArrayList<FoodPostModel> list;

    public UserCategoriesAdapter(Context context, ArrayList<FoodPostModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.simple_categories,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        FoodPostModel model=list.get(position);


        Picasso.get().load(model.getPostImg()).placeholder(R.drawable.programmer).into(holder.binding.imageview);
        holder.binding.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,SingleItemActivity.class);
                intent.putExtra("postedBy",model.getPostedBy());
                intent.putExtra("postId",model.getPostId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SimpleCategoriesBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding=SimpleCategoriesBinding.bind(itemView);
        }
    }
}
