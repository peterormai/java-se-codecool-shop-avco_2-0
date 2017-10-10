package com.codecool.shop.model;

import java.util.ArrayList;


public class Supplier extends BaseModel {
    private ArrayList<Product> products;

    public Supplier(String name, String description) {
        super(name);
        this.products = new ArrayList<>();
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }


    @Override
    public String toString() {
        return name;
    }
}