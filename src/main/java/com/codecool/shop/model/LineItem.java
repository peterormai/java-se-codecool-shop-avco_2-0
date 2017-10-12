package com.codecool.shop.model;


public class LineItem {

    private static int idCounter = 0;
    private int id;
    private Product product;
    private int quantity;
    private int order_id;

    public LineItem(int id, Product product, int quantity, int order_id) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.order_id = order_id;
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

