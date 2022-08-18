package com.example.projectone.pojo;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;
import java.util.Objects;

@IgnoreExtraProperties
public class CartItem {
    @Exclude
    private FoodItem foodItem;
    private String foodItemId;
    private int quantity = 1;
    private String foodItemName;
    private String totalPrice;
    private String pricePerItem;
    private List<AddOn> addOns;

    public CartItem() {
    }

    public CartItem(FoodItem foodItem, int quantity, List<AddOn> addOns) {
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.foodItemId = foodItem.getId();
        this.foodItemName = foodItem.getName();
        this.addOns = addOns;
        this.totalPrice = getTotal();
        this.pricePerItem = foodItem.getPrice();
    }

    public List<AddOn> getAddOns() {
        return addOns;
    }

    public void setAddOns(List<AddOn> addOns) {
        this.addOns = addOns;
    }

    @Exclude
    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public String getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(String foodItemId) {
        this.foodItemId = foodItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(String pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    @Exclude
    @NonNull
    public String getTotal() {
        if (Objects.isNull(foodItem)) {
            return "";
        }
        double d = Double.parseDouble(this.foodItem.getPrice().substring(1)) * this.quantity;
        return String.valueOf(Math.round(d * 100.0) / 100.0);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return foodItemId.equals(cartItem.foodItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodItemId);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "foodItem=" + foodItem +
                ", foodItemId='" + foodItemId + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public void updateTotal() {
        this.totalPrice = getTotal();
    }
}
