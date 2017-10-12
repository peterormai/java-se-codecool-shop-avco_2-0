package com.codecool.shop.dao;

import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;

import java.util.List;

public interface LineItemDao {
    void add(Product product);
    void remove(Product product);
    void removeAll(int id);
    LineItem get(int id);
    List<LineItem> getAll();

}
