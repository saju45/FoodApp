package com.example.foodapp.Activity.Adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activity.Model.FoodPostModel;
import com.example.foodapp.R;
import com.example.foodapp.databinding.SimpleSearchLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.viewHolder>{


    Context context;
    ArrayList<FoodPostModel> list;

    public SearchAdapter(Context context, ArrayList<FoodPostModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.simple_search_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        FoodPostModel model=list.get(position);

        Picasso.get().load(model.getPostImg()).placeholder(R.drawable.programmer).into(holder.binding.image);
        holder.binding.name.setText(model.getFoodName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SimpleSearchLayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SimpleSearchLayoutBinding.bind(itemView);
        }
    }
    public void filterList(ArrayList<FoodPostModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}
