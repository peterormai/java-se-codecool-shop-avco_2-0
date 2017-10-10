package com.codecool.shop.model;


public class LineItem {

    private static int idCounter = 0;
    private int id;
    private Product product;
    private int quantity = 1;

    public LineItem(Product product) {
        this.id = idCounter;
        this.product = product;
        idCounter++;
    }

    public int getId() {
        return id;
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

