package com.example.foodapp.Activity.Adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activity.Model.FoodPostModel;
import com.example.foodapp.Activity.Model.OrderModel;
import com.example.foodapp.R;
import com.example.foodapp.databinding.SimpleUserCartBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserCartAdapter extends RecyclerView.Adapter<UserCartAdapter.viewHolder>{

    Context context;
    ArrayList<OrderModel> list;

    public UserCartAdapter(Context context, ArrayList<OrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.simple_user_cart,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        OrderModel model=list.get(position);

        Picasso.get().load(model.getImage()).placeholder(R.drawable.programmer).into(holder.binding.image);
        holder.binding.name.setText(model.getName());
        holder.binding.foodPrice.setText(model.getPrice()+"");
        holder.binding.price.setText(model.getPrice()+"");
        holder.binding.itemCount.setText(model.getItemCount()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SimpleUserCartBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding=SimpleUserCartBinding.bind(itemView);
        }
    }
}
