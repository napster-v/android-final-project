package com.example.projectone.adapters;

import android.content.Context;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectone.R;
import com.example.projectone.pojo.AddOn;
import com.example.projectone.pojo.Cart;
import com.example.projectone.pojo.CartItem;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.stream.Collectors;

public class CartActivityAdapter extends RecyclerView.Adapter<CartActivityAdapter.CartViewHolder> {
    private final Cart cart;
    private TextView subTotal;
    Context context;
    List<CartItem> cartItems;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public CartActivityAdapter(Context context, List<CartItem> cartItems, Cart cart, TextView subTotal) {
        this.context = context;
        this.cartItems = cartItems;
        this.cart = cart;
        this.subTotal = subTotal;
    }

    @NonNull
    @Override
    public CartActivityAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var inflater = LayoutInflater.from(context);
        var view = inflater.inflate(R.layout.cart_view_row, parent, false);
        return new CartViewHolder(view);
    }

    private String getAddonCombine(CartItem cartItem) {
        return cartItem.getAddOns().stream().map(AddOn::getName).collect(Collectors.joining(", "));
    }

    @Override
    public void onBindViewHolder(@NonNull CartActivityAdapter.CartViewHolder holder, int position) {
        holder.name.setText(cartItems.get(position).getFoodItemName());
        holder.qty.setText(cartItems.get(position).getQuantity() + " X ");
        holder.price.setText("$" + cartItems.get(position).getTotalPrice());
        holder.addOnList.setText(getAddonCombine(cartItems.get(position)));

        holder.removeItem.setOnClickListener(v -> {
            cartItems.remove(holder.getAdapterPosition());
            cart.getCartItems().remove(holder.getAdapterPosition());
            notifyDataSetChanged();
            db.collection("cart").document(cart.getCartId()).set(cart).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                    subTotal.setText("Subtotal: $" + Math.round(cart.getCartTotal() * 100.0) / 100.0 );
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView qty;
        TextView addOnList;
        Button removeItem;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cartItemName);
            price = itemView.findViewById(R.id.totalPrice);
            qty = itemView.findViewById(R.id.quantity);
            removeItem = itemView.findViewById(R.id.removeItem);
            addOnList = itemView.findViewById(R.id.addOnsList);

        }
    }
}
