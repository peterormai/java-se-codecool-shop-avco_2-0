package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private static Order orderInstance = null;
    private static List<LineItem> lineItems = new ArrayList<>();

    public static Order getOrderInstance() {
        return orderInstance;
    }

    public static void setOrderInstance(Order orderInstance) {
        Order.orderInstance = orderInstance;
    }

    public static List<LineItem> getLineItems() {
        return lineItems;
    }

    public static void setLineItems(List<LineItem> lineItems) {
        Order.lineItems = lineItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    private OrderStatus status = OrderStatus.NEW;

}