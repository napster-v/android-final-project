package com.example.projectone.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectone.R;
import com.example.projectone.adapters.AddOnItemViewAdapter;
import com.example.projectone.pojo.AddOn;
import com.example.projectone.pojo.Cart;
import com.example.projectone.pojo.CartItem;
import com.example.projectone.pojo.FoodItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager addOnManager;
    List<AddOn> addOns;
    List<AddOn> addOnList = new ArrayList<>();
    AddOnItemViewAdapter addOnItemManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_screen);

        Intent intent = getIntent();
        FoodItem foodItem = (FoodItem) intent.getSerializableExtra("data");
        addOns = foodItem.getAddOns() == null ? new ArrayList<>() : foodItem.getAddOns();
        TextView name = findViewById(R.id.productName);
        TextView price = findViewById(R.id.productPrice);
        TextView description = findViewById(R.id.productDescription);
        ImageView imageView = findViewById(R.id.productImage);
        TextView currentQty = findViewById(R.id.textView19);
        Button subtractQty = findViewById(R.id.subtractQty);
        Button addQty = findViewById(R.id.addQty);
        Button addToCart = findViewById(R.id.addToCart);

        Log.i(TAG, foodItem.toString());

        addToCart.setOnClickListener(v -> {
            Intent i = new Intent(this, CartActivity.class);

            var uid = FirebaseAuth.getInstance().getUid();
            db.collection("cart").whereEqualTo("userId", uid).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Cart cart = document.toObject(Cart.class);
                        int quantity = Integer.parseInt(String.valueOf(currentQty.getText()));
                        CartItem item = new CartItem(foodItem, quantity, addOnList);
//                        item.setPricePerItem(foodItem.getPrice());
                        cart.addItem(item, quantity);
                        db.collection("cart").document(document.getId()).set(cart).addOnCompleteListener(documentReference -> {
                            Toast.makeText(this, "Item added to cart!", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, documentReference.toString());
                        });
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                    startActivity(i);
                }
            });
        });

        addQty.setOnClickListener(v -> {
            int qty = getQty(currentQty);
            subtractQty.setEnabled(qty > 0);
            currentQty.setText(String.valueOf(qty + 1));
        });

        subtractQty.setOnClickListener(v -> {
            int qty = getQty(currentQty);
            if (qty <= 2) {
                subtractQty.setEnabled(false);
            }
            currentQty.setText(String.valueOf(qty - 1));
        });

        setupAddOnRecyclerView();
        name.setText(foodItem.getName());
        price.setText(foodItem.getPrice());
        description.setText(foodItem.getDescription());
        Glide.with(this).asBitmap().load(foodItem.getImageUrl()).into(imageView);
    }


    private int getQty(TextView currentQty) {
        return Integer.parseInt(String.valueOf(currentQty.getText()));
    }

    private void setupAddOnRecyclerView() {
        recyclerView = findViewById(R.id.addOnRecyclerView);
        addOnItemManager = new AddOnItemViewAdapter(this, addOns, addOnList);
        recyclerView.setAdapter(addOnItemManager);
        addOnManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(addOnManager);
    }
}