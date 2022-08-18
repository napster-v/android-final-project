package com.example.projectone.pojo;

import java.io.Serializable;

public class AddOn implements Serializable {
    private String name, price;

    public AddOn() {
    }

    public AddOn(String name) {
        this.name = name;
    }

    public AddOn(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
