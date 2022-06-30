package com.example.foodapp.Activity.Adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activity.Model.FoodPostModel;
import com.example.foodapp.R;
import com.example.foodapp.databinding.SimplePopularBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserPopularAdapter extends RecyclerView.Adapter<UserPopularAdapter.viewHolder>{

    Context context;
    ArrayList<FoodPostModel> list;

    public UserPopularAdapter(Context context, ArrayList<FoodPostModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.simple_popular,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        FoodPostModel model=list.get(position);

        Picasso.get().load(model.getPostImg()).placeholder(R.drawable.programmer).into(holder.binding.simplePopularImg);
        holder.binding.simplePopularItemName.setText(model.getFoodName());
        holder.binding.simplePopularTime.setText(model.getFoodPrice());

        holder.binding.simplePopularPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "oder is successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        SimplePopularBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding=SimplePopularBinding.bind(itemView);
        }
    }
}
