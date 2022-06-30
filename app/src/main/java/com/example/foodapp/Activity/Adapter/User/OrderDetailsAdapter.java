package com.example.foodapp.Activity.Adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activity.Model.OrderModel;
import com.example.foodapp.R;
import com.example.foodapp.databinding.SimpleMyOrderBinding;

import java.util.ArrayList;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.viewHolder>{

    Context context;
    ArrayList<OrderModel> list;

    public OrderDetailsAdapter(Context context, ArrayList<OrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.simple_my_order,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        OrderModel model=list.get(position);

        holder.binding.itemCount.setText(model.getItemCount()+"");
        holder.binding.price.setText(model.getPrice()+"");
        holder.binding.dateAndtime.setText(model.getTime());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SimpleMyOrderBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SimpleMyOrderBinding.bind(itemView);
        }
    }
}
