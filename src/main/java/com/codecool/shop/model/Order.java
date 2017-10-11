package com.codecool.shop.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private static Order orderInstance = null;
    private static List<LineItem> lineItems = new ArrayList<>();
    private OrderStatus status = OrderStatus.NEW;

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
            LineItem newLineItem = new LineItem(product);
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

    public void changeItemQuantity(String id, String quantity) {
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

    public void setStatus(OrderStatus status) {
        System.out.println("Order status set to " + status);
        this.status = status;
    }

    public OrderStatus getStatus() {
        System.out.println(status);
        return status;
    }

    public void JSONFileWrite() throws IOException {

        JSONObject orders = new JSONObject();
        JSONObject order = new JSONObject();
        JSONArray lineItemsOfOrder = new JSONArray();
        for (LineItem lineItem : lineItems) {
            JSONObject lineItemObj = new JSONObject();
            JSONObject productObj = new JSONObject();
            lineItemObj.put("id", lineItem.getId());
            lineItemObj.put("quantity", lineItem.getQuantity());

            productObj.put("productName", lineItem.getProduct().getName());
            productObj.put("defaultPrice", lineItem.getProduct().getDefaultPrice());
            productObj.put("defaultCurrency", lineItem.getProduct().getDefaultCurrency());
            productObj.put("productCategory", lineItem.getProduct().getProductCategory().getName());
            productObj.put("supplier", lineItem.getProduct().getSupplier().getName());

            lineItemObj.put("product", productObj);
            lineItemsOfOrder.add(lineItemObj);
        }

        orders.put("firstOrder", order);
        order.put("lineItems", lineItemsOfOrder);

        try (FileWriter file = new FileWriter("src/main/resources/files/orders.txt")) {
            file.write(orders.toJSONString());
        }
    }
}