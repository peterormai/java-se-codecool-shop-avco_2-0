package com.codecool.shop.dao;

import com.codecool.shop.model.LineItem;

import java.util.List;

public interface LineItemDao {
    void add(LineItem lineItem);
    void remove(int id);
    LineItem get(int id);
    List<LineItem> getAll();

}
