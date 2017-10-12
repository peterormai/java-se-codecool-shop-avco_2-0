package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LineItemDaoJdbc implements LineItemDao {
    private LineItemDaoJdbc lineItemDaoJdbc = null;

    private LineItemDaoJdbc() {
    }

    public LineItemDaoJdbc getInstance() {
        if (lineItemDaoJdbc == null) {
            lineItemDaoJdbc = new LineItemDaoJdbc();
        }
        return lineItemDaoJdbc;
    }

    @Override
    public void add(LineItem newLineItem) {
        List<LineItem> lineItemList = this.getAll();
        for (LineItem lineItem : lineItemList) {
            if (lineItem.getProduct().equals(newLineItem.getProduct())) {
                lineItem.setQuantity(lineItem.getQuantity() + 1);
            }
        }
        String query = "UPDATE ";

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public LineItem get(int id) {
        return null;
    }

    @Override
    public List<LineItem> getAll() {

        String query = "SELECT * FROM linteItem";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<LineItem> lineItemList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int product_id = resultSet.getInt("product_id");
                Product product = ProductDaoJdbc.getInstance().find(product_id);
                int quantity = resultSet.getInt("quantity");
                int order_id = resultSet.getInt("order_id");
                LineItem result = new LineItem(id,product,quantity,order_id);
                lineItemList.add(result);
            }
            return lineItemList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return new ConnectionManager("src/main/resources/sql/config.txt").getConnection();
    }
}
