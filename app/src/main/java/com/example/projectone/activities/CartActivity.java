package com.example.projectone.activities;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectone.R;
import com.example.projectone.adapters.CartActivityAdapter;
import com.example.projectone.pojo.Cart;
import com.example.projectone.pojo.CartItem;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class CartActivity extends AppCompatActivity {
    private final List<CartItem> cartItems = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Cart cart;
    double sum = 0;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_view);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String cartId = preferences.getString("cartId", "NA");
        TextView subTotal = findViewById(R.id.subTotal);
        Button checkoutBtn = findViewById(R.id.checkoutBtn);
        RecyclerView cartItemList = findViewById(R.id.cartItemList);
        ProgressBar progressBar = findViewById(R.id.progressBar);


        checkoutBtn.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                Intent i = new Intent(this, CheckoutActivity.class);
                i.putExtra("subTotal", cart.getCartTotal());
                startActivity(i);
            } else {
                Toast.makeText(this, "Please add few items to proceed.", Toast.LENGTH_SHORT).show();
            }


        });

        db.collection("cart").document(cartId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, cartId);
                cart = task.getResult().toObject(Cart.class);
                cart.setCartId(task.getResult().getId());
                cartItems.addAll(cart.getCartItems());
//                sum = cartItems.stream().map(cartItem -> Double.parseDouble(cartItem.getTotalPrice()) * (double) cartItem.getQuantity()).mapToDouble(Double::doubleValue).sum();
                Log.i(TAG, String.format("List2 of CIs: %s", cartItems));
                var adapter = new CartActivityAdapter(this, cartItems, cart, subTotal);
                var layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                cartItemList.setLayoutManager(layoutManager);
                cartItemList.setAdapter(adapter);
                subTotal.setText("Subtotal: $" + Math.round(cart.getCartTotal() * 100.0) / 100.0);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}