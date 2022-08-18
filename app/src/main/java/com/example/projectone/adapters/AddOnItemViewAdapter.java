package com.example.projectone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectone.R;
import com.example.projectone.pojo.AddOn;

import java.util.List;

public class AddOnItemViewAdapter extends RecyclerView.Adapter<AddOnItemViewAdapter.VH> {
    private final Context context;
    private final List<AddOn> addOns;
    private List<AddOn> addOnList;

    public AddOnItemViewAdapter(Context context, List<AddOn> addOns, List<AddOn> addOnList) {
        this.context = context;
        this.addOns = addOns;
        this.addOnList = addOnList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_on_row, parent, false);
        return new AddOnItemViewAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.itemName.setText(addOns.get(position).getName());
//        holder.itemPrice.setText(addOns.get(position).getPrice());

        holder.itemName.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                addOnList.add(addOns.get(holder.getAdapterPosition()));
                Toast.makeText(context, String.format("Check at %d", position), Toast.LENGTH_SHORT).show();
            } else {
                addOnList.remove(addOns.get(holder.getAdapterPosition()));
                Toast.makeText(context, String.format("Uncheck at %d", position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return addOns.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        CheckBox itemName;
        TextView itemPrice;

        public VH(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.addOnItem);
//            itemPrice = itemView.findViewById(R.id.addOnPrice);
        }
    }
}
