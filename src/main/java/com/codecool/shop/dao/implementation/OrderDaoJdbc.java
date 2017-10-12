package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.ConnectionManager;
import com.codecool.shop.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoJdbc implements OrderDao {

    @Override
    public void add(Order order) {
        String query = "INSERT INTO orders default values ";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM orders WHERE id=?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,1);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order get(int id) {
        String query = "SELECT * FROM orders WHERE id=?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Order.getInstance();
    }

    @Override
    public List<Order> getAll() {
        List<Order> orderList = new ArrayList<>();
        return orderList;
    }

    private Connection getConnection() throws SQLException {
        return new ConnectionManager("src/main/resources/sql/config.txt").getConnection();
    }
}
