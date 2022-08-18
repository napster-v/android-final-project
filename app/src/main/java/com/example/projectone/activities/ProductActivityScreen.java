package com.example.projectone.activities;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectone.R;
import com.example.projectone.RecyclerViewInterface;
import com.example.projectone.adapters.CategoryViewAdapter;
import com.example.projectone.adapters.FoodItemViewAdapter;
import com.example.projectone.pojo.AddOn;
import com.example.projectone.pojo.Category;
import com.example.projectone.pojo.FoodItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductActivityScreen extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager categoryManager;
    RecyclerView.LayoutManager foodItemManager;
    List<Category> categories = new ArrayList<>();
    List<FoodItem> foodItems = new ArrayList<>();
    FoodItemViewAdapter foodItemViewAdapter;
    CategoryViewAdapter categoryViewAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);

        FloatingActionButton cartButton = findViewById(R.id.cartButton);
        FloatingActionButton reset = findViewById(R.id.resetFilter);

/*
        List.of(new FoodItem("Original Burger Combo", "Juicy, flame-grilled burger made with 100%" +
                        " " +
                        "Canadian beef, placed on a fresh, lightly toasted bun and topped just " +
                        "the way " +
                        "you want it. Served with your choice of a side and a drink.", "$11.39",
                        "https" +
                                "://d1ralsognjng37.cloudfront" +
                                ".net/ca206a3b-282f-4648-abe0-71cff4afb10e" +
                                ".jpeg",
                        "Fast Food", List.of(new AddOn("Cheese"), new AddOn("Bacon"), new AddOn(
                        "Onion " +
                                "Rings"))),

                new FoodItem("Buffalo Chicken Wrap Combo", "Crispy Buffalo Chicken plus your " +
                        "choice of " +
                        "toppings and sauces, all wrapped together in a grilled flour tortilla. " +
                        "Made with" +
                        " 100% Canadian Chicken. Served with your choice of a side and a drink.",
                        "$9.19"
                        , "https://d1ralsognjng37.cloudfront" +
                        ".net/9fb6980a-a47f-4154-9d7e-dd0d542da849.jpeg",
                        "Fast Food", List.of(new AddOn("Shredded Cheese"), new AddOn("Bacon"))),

                new FoodItem("Crispy Chicken Sandwich", "The all new Harv's Crispy Chicken " +
                        "Sandwich - a " +
                        "juicier and more flavourful, 100% Canadian chicken breast, with a " +
                        "signature " +
                        "blend of spices.", "$11.39", "https://d1ralsognjng37.cloudfront" +
                        ".net/03a5a60e-67b8-4ffb-a91e-3545895ad6e5.jpeg",
                        "Fast Food", List.of(new AddOn("Cheese"), new AddOn("Bacon"), new AddOn(
                        "Onion " +
                                "Rings"))),

                new FoodItem("5 Chicken Strips", "Adults and youth (ages 13 and older) need an " +
                        "average of" +
                        " 2,000 calories a day, and children (ages 4 to 12) need an average of 1," +
                        "500 " +
                        "calories a day. However, individual needs vary.", "$10.71", "https" +
                        "://d1ralsognjng37.cloudfront.net/76289c8c-f0fd-4b09-9c89-30d0e48d9682" +
                        ".jpeg",
                        "Fast Food", List.of(new AddOn("Honey Mustard"), new AddOn("BBQ"),
                        new AddOn(
                                "Sweet & Sour"))),

                new FoodItem("5 Chicken Strips", "Adults and youth (ages 13 and older) need an " +
                        "average of" +
                        " 2,000 calories a day, and children (ages 4 to 12) need an average of 1," +
                        "500 " +
                        "calories a day. However, individual needs vary.", "$10.71", "https" +
                        "://d1ralsognjng37.cloudfront.net/76289c8c-f0fd-4b09-9c89-30d0e48d9682" +
                        ".jpeg",
                        "American", List.of(new AddOn("Honey Mustard"), new AddOn("BBQ"), new AddOn(
                        "Sweet & Sour"))),

                new FoodItem("Deep-fried Dumpling (10 pcs)", "Perfectly Deep fried crisp " +
                        "dumplings, with " +
                        "your choice of meat or vegetable filling. served with dipping sauces",
                        "$7.99",
                        "https://d1ralsognjng37.cloudfront" +
                                ".net/39929e89-aadd-4b51-8c4c-b2a733b2d0be.jpeg",
                        "Fast Food", List.of(new AddOn("Kimchi"), new AddOn("BBQ"))),

                new FoodItem("Deep-fried Dumpling (10 pcs)", "Perfectly Deep fried crisp " +
                        "dumplings, with " +
                        "your choice of meat or vegetable filling. served with dipping sauces",
                        "$7.99",
                        "https://d1ralsognjng37.cloudfront" +
                                ".net/39929e89-aadd-4b51-8c4c-b2a733b2d0be.jpeg",
                        "Chinese", List.of(new AddOn("Kimchi"), new AddOn("BBQ"))),

                new FoodItem("Deep-fried Dumpling (10 pcs)", "Perfectly Deep fried crisp " +
                        "dumplings, with " +
                        "your choice of meat or vegetable filling. served with dipping sauces",
                        "$7.99",
                        "https://d1ralsognjng37.cloudfront" +
                                ".net/39929e89-aadd-4b51-8c4c-b2a733b2d0be.jpeg",
                        "Comfort Food", List.of(new AddOn("Kimchi"), new AddOn("BBQ"))),

                new FoodItem("Insalata Caesar", "Crisp romaine lettuce, bacon, Parmesan, and " +
                        "creamy " +
                        "garlic dressing with two pieces of garlic bread.", "$17.50", "https" +
                        "://d1ralsognjng37.cloudfront.net/89df7423-d09e-4d69-9876-8b393417085a" +
                        ".jpeg",
                        "Comfort Food", List.of(new AddOn("Extra Cheese"))),

                new FoodItem("Shrimp Zafferano", "Seared garlic shrimp served over linguine in a " +
                        "roasted " +
                        "pepper-saffron cream reduction with grilled zucchini and a hint of " +
                        "citrus.",
                        "$27.00", "https://d1ralsognjng37.cloudfront" +
                        ".net/cd5be1aa-7b15-4d1b-8bd8-c93167d4b726.jpeg",
                        "Comfort Food", List.of())).forEach(x -> db.collection("foodItems").add
                        (x).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "onCreate: Product added ");
            }
        }));
*/

/*
        List.of(new FoodItem("Turkey Bacon Ranch ™, Large (11-12 inch)", "Smoked turkey breast, " +
                        "pepper bacon, sharp cheddar cheese topped with peppercorn ranch, mayo, " +
                        "lettuce, tomato, and onion.", "$19",
                        "https://tb-static.uber.com/prod/image-proc/processed_images" +
                                "/49932eabd3f44bdb3d96f7d3dfd2dc67" +
                                "/5954bcb006b10dbfd0bc160f6370faf3.jpeg",
                        "Sandwich", List.of(new AddOn("Wheat Bread"), new AddOn("Pickle"))),

                new FoodItem("Philly Cheese Steak Sandwich", "The classic. Your choice of steak " +
                        "or chicken with your choice of cheese, toppings, and sauces.",
                        "$10.39"
                        , "https://tb-static.uber.com/prod/image-proc/processed_images" +
                        "/6564152484f4c7bb3e9835ee8e784f5c/859baff1d76042a45e319d1de80aec7a.jpeg",
                        "Sandwich", List.of(new AddOn("Bacon"), new AddOn("Onion"), new AddOn(
                        "Pulled Pork"))),

                new FoodItem("Triple Cheese Melt Sandwich", "An ooey-gooey combination of steak " +
                        "or chicken, with your choice of three cheeses melted to perfection, and " +
                        "topped with your choice of toppings and sauces.", "$12.09", "https://tb" +
                        "-static.uber" +
                        ".com/prod/image-proc/processed_images/5b26ab660ee75c45af97d6ebd1bc022f" +
                        "/859baff1d76042a45e319d1de80aec7a.jpeg",
                        "Sandwich", List.of(new AddOn("Bacon"), new AddOn("Onion"), new AddOn(
                        "Pulled Pork"), new AddOn("Cheese Whiz"))),

                new FoodItem("Raj Kachori", "Puffed rice, masala peanuts, red onions, potatoes, " +
                        "green mango, and tomatoes, generously covered with sweet tamarind sauce," +
                        " hot coriander chutney, and yogurt.", "$9.00", "https://tb" +
                        "-static.uber.com/prod/image-proc/process…eda94bdd33f" +
                        "/859baff1d76042a45e319d1de80aec7a.jpeg",
                        "Indian", List.of(new AddOn("Green Chutney"), new AddOn("Sweet Chutney"),
                        new AddOn("Imli Chutney"))),

                new FoodItem("Madras chicken curry", "Madras chicken curry", "$12.55", "https" +
                        "://tb" +
                        "-static.uber.com/prod/image-proc/processed_images" +
                        "/58c71281e83f63c6ff6706ccbbdca6cf/4218ca1d09174218364162cd0b1a8cc1.jpeg",
                        "Indian", List.of()),

                new FoodItem("Bathinda Special Butter Chicken", "Boneless tandoori chicken cooked" +
                        " with rich tomato cream gravy.",
                        "$18.95",
                        "https://tb-static.uber.com/prod/image-proc/processed_images" +
                                "/38734dbbdd6ae03fa009268bca3274ea" +
                                "/4218ca1d09174218364162cd0b1a8cc1.jpeg",
                        "Indian", List.of(new AddOn("Extra Butter"))),

                new FoodItem("Chicken Biryani", "Fresh basmati rice cooked with marinated " +
                        "chicken, bell peppers, and onions infused with Indian spices.",
                        "$7.99",
                        "https://tb-static.uber.com/prod/image-proc/processed_images" +
                                "/e7f990bcbaba51596b916b8da62b5d6c" +
                                "/4218ca1d09174218364162cd0b1a8cc1.jpeg",
                        "Indian", List.of(new AddOn("Raita"), new AddOn("Special Raita"))),

                new FoodItem("Tandoori Chicken", "Chicken (leg and thigh) marinated in tandoori " +
                        "spices and baked slowly in a tandoori oven.",
                        "$15.95",
                        "https://tb-static.uber.com/prod/image-proc/processed_images" +
                                "/c232f2879dc4990dc399c26ee31c5d16" +
                                "/4218ca1d09174218364162cd0b1a8cc1.jpeg",
                        "Indian", List.of(new AddOn("Green Chutney"), new AddOn("Sweet Chutney"),
                        new AddOn("Imli Chutney"))),

                new FoodItem("Seekh Kebab", "Spiced ground chicken with chopped bell peppers and " +
                        "onions formed into cylinders.", "$15.95", "https://tb-static.uber" +
                        ".com/prod/image-proc/processed_images/3442f9558f09d32043d685365a99ea0f" +
                        "/4218ca1d09174218364162cd0b1a8cc1.jpeg",
                        "Indian", List.of(new AddOn("Green Chutney"))),

                new FoodItem("Dal Makhni", "Lentils are cooked with onions, tomatoes, and spices.",
                        "$15.95", "https://tb-static.uber.com/prod/image-proc/processed_images" +
                        "/7c2009955414de56ac1acc195eb3242d/4218ca1d09174218364162cd0b1a8cc1.jpeg",
                        "Indian", List.of())).forEach(x -> db.collection("foodItems").add
                (x).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "onCreate: Product added ");
            }
        }));
*/


       /* List.of(new FoodItem("Veg. Samosa", "Hand made crisp and delicious pastry with a generous" +
                " amount of savory potato mixture made of various spices, onions, peas etc , The " +
                "richness of flavour will remind you of the samosas made at home. Enjoy your " +
                "favorite samosa only at HomeTaste", "$1.75",
                "https://tb-static.uber.com/prod/image-proc/processed_images" +
                        "/713e07842b0bdf171e76c5787d1f16a7/5954bcb006b10dbfd0bc160f6370faf3.jpeg",
                "Indian", List.of(new AddOn("Green Chutney"), new AddOn("Sweet Chutney"),
                new AddOn("Imli Chutney")))).forEach(x -> db.collection("foodItems").add
                (x).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "onCreate: Product added ");
            }
        }));*/


        // Create a new user with a first and last name
        db.collection("categories").orderBy("name").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                task.getResult().forEach(queryDocumentSnapshot -> {
                    categories.add(queryDocumentSnapshot.toObject(Category.class));
                });
                categoryViewAdapter.notifyDataSetChanged();
            }
        });

        getFoodItems();

        setupCategoryRecyclerView();
        setupFoodItemRecyclerView();

        cartButton.setOnClickListener(v -> {
            Intent i = new Intent(this, CartActivity.class);
            startActivity(i);
        });

        reset.setOnClickListener(v -> {
            Toast.makeText(this, "Filters cleared!", Toast.LENGTH_SHORT).show();
            getFoodItems();
        });

    }

    private void getFoodItems() {
        db.collection("foodItems").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                task.getResult().forEach(queryDocumentSnapshot -> {
                    Log.i(TAG, queryDocumentSnapshot.getId());
                    var foodItem = queryDocumentSnapshot.toObject(FoodItem.class);
                    foodItem.setId(queryDocumentSnapshot.getId());
                    if (!foodItems.contains(foodItem)) {
                        foodItems.add(foodItem);
                    }
                });
//                foodItems = new ArrayList<>(new HashSet<>(foodItems));
                foodItemViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setupFoodItemRecyclerView() {
        RecyclerView foodItemRV = findViewById(R.id.foodItems);
        foodItemViewAdapter = new FoodItemViewAdapter(this, foodItems);
        foodItemRV.setAdapter(foodItemViewAdapter);
        foodItemManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        foodItemRV.setLayoutManager(foodItemManager);
    }

    private void setupCategoryRecyclerView() {
        recyclerView = findViewById(R.id.rView);
        categoryViewAdapter = new CategoryViewAdapter(this, categories, this);
        recyclerView.setAdapter(categoryViewAdapter);
        categoryManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(this.categoryManager);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemClick(int position) {
        foodItems.clear();
        Log.i("INFO", String.valueOf(position));
        Log.i("INFO", String.valueOf(categories.get(position)));
        db.collection("foodItems").whereEqualTo("category", categories.get(position).getName()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                task.getResult().forEach(queryDocumentSnapshot -> {
                    var foodItem = queryDocumentSnapshot.toObject(FoodItem.class);
                    foodItem.setId(queryDocumentSnapshot.getId());
                    foodItems.add(foodItem);
                });
                foodItemViewAdapter.notifyDataSetChanged();
            }
        });
    }
}