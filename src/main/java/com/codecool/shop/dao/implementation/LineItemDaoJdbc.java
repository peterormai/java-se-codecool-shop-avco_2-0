package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineItemDaoJdbc implements LineItemDao {
    private static LineItemDaoJdbc lineItemDaoJdbc = null;

    private LineItemDaoJdbc() {
    }

    public static LineItemDaoJdbc getInstance() {
        if (lineItemDaoJdbc == null) {
            lineItemDaoJdbc = new LineItemDaoJdbc();
        }
        return lineItemDaoJdbc;
    }

    @Override
    public void add(Product product) {
        List<LineItem> lineItems = this.getAll();
        List<Integer> values = new ArrayList<>();
        String query = "";
        for (LineItem lineItem : lineItems) {
            if (lineItem.getProduct().equals(product)) {
                query = "UPDATE linteitem SET quantity = ? WHERE product_id = ?";
                values.add(lineItem.getQuantity() + 1);
                break;
            }
        }
        if (query == "") {
            query = "INSERT INTO lineitem(product_id,quantity,order_id";
            values.add(product.getId());
            values.add(1);
            values.add(1);
        }

        manageLinItemDataConnection(values, query);


    }

    @Override
    public void remove(Product product) {
        List<LineItem> lineItems = this.getAll();
        List<Integer> values = new ArrayList<>();
        String query = "";
        for (LineItem lineItem : lineItems) {
            if (lineItem.getProduct().equals(product)) {
                if (lineItem.getQuantity() - 1 == 0) {
                    query = "DELETE FROM lineitem WHERE id = ?";
                } else {
                    query = "UPDATE linteitem SET quantity = ? WHERE product_id = ?";
                    values.add(lineItem.getQuantity() - 1);
                    values.add(product.getId());

                }
                break;
            }
        }
        manageLinItemDataConnection(values, query);

    }

    @Override
    public void removeAll(int id) {

    }

    @Override
    public LineItem get(int id) {
        return null;
    }

    private void manageLinItemDataConnection(List<Integer> values, String query) {

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i < values.size(); i++) {
                statement.setInt(i + 1, values.get(i));
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<LineItem> getAll() {

        String query = "SELECT * FROM lineitem;";
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
                LineItem result = new LineItem(id, product, quantity, order_id);
                lineItemList.add(result);
            }
            return lineItemList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getTotalPrice() {
        List<LineItem> lineItems = getAll();
        int totalPrice = 0;
        for (LineItem lineItem : lineItems) {
            totalPrice += lineItem.getLineItemsPrice();
        }
        return totalPrice;

    }

    @Override
    public int getNumberOfItem() {

        List<LineItem> lineItems = getAll();

        int number = 0;
        for (LineItem lineItem : lineItems) {
            number += lineItem.getQuantity();
        }
        return number;
    }

    private Connection getConnection() throws SQLException {
        return new ConnectionManager("src/main/resources/sql/config.txt").getConnection();
    }
}
