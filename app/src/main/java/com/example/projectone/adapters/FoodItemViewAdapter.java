package com.example.projectone.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectone.R;
import com.example.projectone.activities.ProductDetailActivity;
import com.example.projectone.pojo.FoodItem;

import java.util.List;

public class FoodItemViewAdapter extends RecyclerView.Adapter<FoodItemViewAdapter.VH> {
    Context context;
    private final List<FoodItem> foodItemList;

    public FoodItemViewAdapter(Context context, List<FoodItem> foodItemList) {
        this.context = context;
        this.foodItemList = foodItemList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.food_item, parent, false);
        return new FoodItemViewAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.foodItemName.setText(foodItemList.get(position).getName());
        holder.foodItemDescription.setText(foodItemList.get(position).getDescription());
        holder.foodItemPrice.setText(foodItemList.get(position).getPrice());
        Glide.with(context).asBitmap().load(foodItemList.get(position).getImageUrl()).into(holder.foodItemImage);

        holder.foodItemCard.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("data", foodItemList.get(position));
            context.startActivity(intent);
        });
    }

    public void clearData() {
        this.foodItemList.clear();
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView foodItemName, foodItemDescription, foodItemPrice;
        ImageView foodItemImage;
        CardView foodItemCard;

        public VH(@NonNull View itemView) {
            super(itemView);
            foodItemName = itemView.findViewById(R.id.foodItemName);
            foodItemDescription = itemView.findViewById(R.id.foodItemDescription);
            foodItemPrice = itemView.findViewById(R.id.foodItemPrice);
            foodItemImage = itemView.findViewById(R.id.foodItemImage);
            foodItemCard = itemView.findViewById(R.id.foodItemCard);
        }
    }
}
