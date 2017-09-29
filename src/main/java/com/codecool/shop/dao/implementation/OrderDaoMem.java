package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements OrderDao{

    private static OrderDaoMem orderDaoMemInstance = null;
    private static List<Order> orders = new ArrayList<>();

    public static OrderDaoMem getInstance() {
        if (orderDaoMemInstance == null) {
            orderDaoMemInstance = new OrderDaoMem();
        }
        return orderDaoMemInstance;
    }

    private OrderDaoMem() {
    }

    @Override
    public void add(Order order) {
        orders.add(order);
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Order get(int id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }
}
