package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

public interface CartDao {

    void add(Product cart);
    void remove(int id);
}
