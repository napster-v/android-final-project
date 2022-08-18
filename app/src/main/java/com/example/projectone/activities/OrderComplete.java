package com.example.projectone.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectone.R;
import com.example.projectone.adapters.CartActivityAdapter;
import com.example.projectone.pojo.Cart;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderComplete extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);

        Button shopMore = findViewById(R.id.shopMoreButton);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String cartId = preferences.getString("cartId", "NA");
        db.collection("cart").document(cartId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, cartId);
                Cart cart = task.getResult().toObject(Cart.class);
                cart.setCartId(task.getResult().getId());
                cart.getCartItems().clear();
                db.collection("cart").document(cartId).set(cart).addOnCompleteListener(x -> {
                    if (x.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        shopMore.setOnClickListener(v->{
            var i = new Intent(this, ProductActivityScreen.class);
            startActivity(i);
        });
    }
}