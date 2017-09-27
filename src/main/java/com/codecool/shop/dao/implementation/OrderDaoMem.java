package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements OrderDao {
    private static OrderDaoMem ourInstance = null;
    private static List<LineItem> lineItems = new ArrayList<>();

    public static OrderDaoMem getInstance() {
        if (ourInstance == null) {
            ourInstance = new OrderDaoMem();
        }
        return ourInstance;
    }

    private OrderDaoMem() {
    }

    @Override
    public void add(Product product) {
        String searchingName = product.getName();
        boolean isNotIn = true;

        for (LineItem lineItem : lineItems) {
            if (lineItem.getProductName().equals(searchingName)) {
                int newQuantity = lineItem.getQuantity() + 1;
                lineItem.setQuantity(newQuantity);
                isNotIn = false;
            }
        }
        if (isNotIn) {
            LineItem newLineItem = new LineItem(product.getId(), product.getName(), product.getDefaultPrice());
            lineItems.add(newLineItem);
        }
        lineItemTest();

    }

    public int numberOfLineItems() {
        int numberOfItems = 0;
        for (LineItem lineItem : lineItems) {
            numberOfItems += lineItem.getQuantity();
        }
        return numberOfItems;
    }

    public static void lineItemTest() {
        for (LineItem lineItem : lineItems) {
            System.out.print("id: " + lineItem.getId());
            System.out.print(" name: " + lineItem.getProductName());
            System.out.println(" quantity: " + lineItem.getQuantity());
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<LineItem> getAll() {
        return lineItems;
    }

    public float getTotalPrice() {
        float totalPrice = 0;
        for (LineItem lineItem : lineItems) {
            totalPrice += lineItem.getPrice() * lineItem.getQuantity();
        }
        return totalPrice;
    }
}