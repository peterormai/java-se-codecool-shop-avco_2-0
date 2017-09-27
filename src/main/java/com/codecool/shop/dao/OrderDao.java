package com.codecool.shop.dao;

import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;

import javax.sound.sampled.Line;
import java.util.List;

public interface OrderDao {

    void add(Product product);
    void remove(int id);

    List<LineItem> getAll();
    boolean changeItemValue(String id, String quantity);
    float getTotalPrice();
}
