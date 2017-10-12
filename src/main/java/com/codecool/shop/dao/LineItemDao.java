package com.codecool.shop.dao;

import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.OrderStatus;
import com.codecool.shop.model.Product;

import java.util.List;

public interface LineItemDao {
    void add(Product product);
    void remove(Product product);
    void removeLineItem(int id);
    void removeAll();
    LineItem get(int id);
    List<LineItem> getAll();
     int getNumberOfItem();
    int getTotalPrice();
    void changeItemQuantity(int id, int quantity);
    void JSONFileWrite();

}
