package com.codecool.shop.dao;

import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;

import java.util.List;

public interface OrderDao {

    void add(Product product);
    void remove(int id);

    List<LineItem> getAll();
    float getTotalPrice();
}
