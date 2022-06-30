package com.example.foodapp.Activity.Adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activity.Model.FoodPostModel;
import com.example.foodapp.R;
import com.example.foodapp.databinding.SimpleSpecialOffersBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserOfferAdapter extends RecyclerView.Adapter<UserOfferAdapter.viewHolder>{

    Context context;
    ArrayList<FoodPostModel> list;

    public UserOfferAdapter(Context context, ArrayList<FoodPostModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.simple_special_offers,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        FoodPostModel foodPostModel=list.get(position);

        Picasso.get().load(foodPostModel.getPostImg()).placeholder(R.drawable.programmer).into(holder.binding.simpleSpecialImg);
        holder.binding.simpleSpecialItemName.setText(foodPostModel.getFoodName());
        holder.binding.simpleSpecialNewprice.setText(foodPostModel.getFoodNewPrice());
        holder.binding.simpleSpecialOldPrice.setText(foodPostModel.getFoodPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SimpleSpecialOffersBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SimpleSpecialOffersBinding.bind(itemView);
        }
    }
}
