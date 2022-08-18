package com.example.projectone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectone.R;
import com.example.projectone.RecyclerViewInterface;
import com.example.projectone.pojo.Category;
import com.example.projectone.pojo.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewAdapter extends RecyclerView.Adapter<CategoryViewAdapter.ViewHolder> {
    Context context;
    private final List<Category> productList;
    RecyclerView.LayoutManager foodItemManager;
    RecyclerView foodItemView;
    List<FoodItem> foodItems = new ArrayList<>();
    RecyclerViewInterface recyclerViewInterface;

    public CategoryViewAdapter(Context context, List<Category> productList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.productList = productList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        foodItemView = parent.getRootView().findViewById(R.id.foodItems);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameOne.setText(productList.get(position).getName());
        Glide.with(context).asBitmap().load(productList.get(position).getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameOne;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            nameOne = itemView.findViewById(R.id.card_name_1);
            imageView = itemView.findViewById(R.id.card_image_1);
            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }
}
