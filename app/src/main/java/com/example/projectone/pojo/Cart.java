package com.example.projectone.pojo;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String userId;
    @Exclude
    private String cartId = "";
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart(String userId) {
        this.userId = userId;
    }

    public Cart() {
    }

    public double getCartTotal() {
        return cartItems.stream().map(cartItem -> Double.parseDouble(cartItem.getPricePerItem().substring(1)) * (double) cartItem.getQuantity()).mapToDouble(Double::doubleValue).sum();
    }

    @Exclude
    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void addItem(CartItem cartItem, int quantity) {
        if (cartItems.contains(cartItem)) {
            var item = cartItems.get(cartItems.indexOf(cartItem));
            item.setFoodItem(cartItem.getFoodItem());
            item.setQuantity(item.getQuantity() + quantity);
            item.updateTotal();
        } else {
            this.cartItems.add(cartItem);
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId='" + userId + '\'' +
                ", cartItems=" + cartItems +
                '}';
    }
}
