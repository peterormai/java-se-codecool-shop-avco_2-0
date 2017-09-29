package com.codecool.shop.model;


public class LineItem {

    private int id;
    private Product product;
    private int quantity = 1;
    private float price;

    public LineItem(int id, Product product, float price) {
        this.id = id;
        this.product = product;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

}

