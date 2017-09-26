package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

public interface OrderDao {

    void add(Product product);
    void remove(int id);
}
