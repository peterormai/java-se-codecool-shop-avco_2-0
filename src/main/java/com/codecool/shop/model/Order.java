package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private static Order orderInstance = null;
    private static List<LineItem> lineItems = new ArrayList<>();

    public static Order getInstance() {
        if (orderInstance == null) {
            orderInstance = new Order();
        }
        return orderInstance;
    }

    private Order() {
    }

    public void add(Product product) {
        boolean isNotIn = true;

        for (LineItem lineItem : lineItems) {
            if (lineItem.getProduct().equals(product)) {
                int newQuantity = lineItem.getQuantity() + 1;
                lineItem.setQuantity(newQuantity);
                isNotIn = false;
            }
        }
        if (isNotIn) {
            LineItem newLineItem = new LineItem(product.getId(), product);
            lineItems.add(newLineItem);
        }
    }

    public int numberOfItems() {
        int numberOfItems = 0;
        for (LineItem lineItem : lineItems) {
            numberOfItems += lineItem.getQuantity();
        }
        return numberOfItems;
    }

    public void removeLineItem(int id) {
        for (LineItem item : lineItems) {
            if (item.getId() == id) {
                lineItems.remove(item);
                break;
            }
        }
    }

    public void removeAll() {
        lineItems = new ArrayList<>();
    }


    public List<LineItem> getAll() {
        return lineItems;
    }

    public float getTotalPrice() {
        float totalPrice = 0;
        for (LineItem lineItem : lineItems) {
            totalPrice += lineItem.getLineItemsPrice();
        }
        return totalPrice;
    }

    public void changeItemQuantity (String id, String quantity) {
        int num = Integer.parseInt(quantity);
        int validId = Integer.parseInt(id);
        if (num == 0) {
            removeLineItem(validId);
        } else {
            for (LineItem item : lineItems) {
                if (item.getId() == validId) {
                    item.setQuantity(num);
                }
            }
        }
    }
}