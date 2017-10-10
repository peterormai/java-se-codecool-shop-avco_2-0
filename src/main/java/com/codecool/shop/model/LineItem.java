package com.codecool.shop.model;


public class LineItem {

    private int id;
    private Product product;
    private int quantity = 1;

    public LineItem(int id, Product product) {
        this.id = id;
        this.product = product;
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

    public float getLineItemsPrice() {
        return product.getDefaultPrice() * quantity;
    }

}

