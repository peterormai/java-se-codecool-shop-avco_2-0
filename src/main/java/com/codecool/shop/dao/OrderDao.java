package com.codecool.shop.dao;

import com.codecool.shop.model.Order;
import java.util.List;

public interface OrderDao {

    void add(Order order);

    void remove(int id);
    Order get(int id);
    List<Order> getAll();
}
