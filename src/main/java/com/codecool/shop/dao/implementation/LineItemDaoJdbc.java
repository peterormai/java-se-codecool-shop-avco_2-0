package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.omg.CORBA.INTERNAL;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineItemDaoJdbc implements LineItemDao {
    private static LineItemDaoJdbc lineItemDaoJdbc = null;
    private OrderStatus status = OrderStatus.NEW;


    @Override
    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }


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
            if (lineItem.getProduct().getId() == product.getId()) {
                query = "UPDATE lineitem SET quantity = ? WHERE product_id = ?;";
                values.add(lineItem.getQuantity() + 1);
                values.add(product.getId());
                break;
            }
        }
        if (query == "") {
            query = "INSERT INTO lineitem(product_id,quantity,order_id)VALUES(?,?,?);";
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
            System.out.println(product.getId());
            if (lineItem.getProduct().getId() == product.getId()) {
                if (lineItem.getQuantity() - 1 == 0) {
                    query = "DELETE FROM lineitem WHERE id = ?";
                    values.add(product.getId());
                } else {
                    query = "UPDATE lineitem SET quantity = ? WHERE product_id = ?";
                    values.add(lineItem.getQuantity() - 1);
                    values.add(product.getId());

                }
                break;
            }
        }
        manageLinItemDataConnection(values, query);

    }

    @Override
    public void removeLineItem(int id) {
        String query = "DELETE FROM lineitem WHERE id = ?";
        List<Integer> value = new ArrayList<>();
        value.add(id);
        manageLinItemDataConnection(value, query);

    }

    @Override
    public void removeAll() {
        String query = "DELETE FROM lineitem";
        List<Integer> value = new ArrayList<>();

        manageLinItemDataConnection(value, query);

    }

    @Override
    public LineItem get(int id) {
        String query = "SELECT * FROM lineitem where id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int product_id = resultSet.getInt("product_id");
                Product product = ProductDaoJdbc.getInstance().find(product_id);
                int quantity = resultSet.getInt("quantity");
                int order_id = resultSet.getInt("order_id");
                LineItem lineItem = new LineItem(id, product, quantity, order_id);
                return lineItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void changeItemQuantity(int id, int quantity) {
        String query;
        List<Integer> values = new ArrayList<>();
        if (quantity == 0){
            query = "DELETE FROM lineitem WHERE id = ?";
            values.add(id);
        } else {

            query = "UPDATE lineitem SET quantity = ? WHERE id = ?";
            values.add(quantity);
            values.add(id);
        }
        manageLinItemDataConnection(values, query);
    }

    @Override
    public void JSONFileWrite() {

        JSONObject orders = new JSONObject();
        JSONObject order = new JSONObject();
        JSONArray lineItemsOfOrder = new JSONArray();
        for (LineItem lineItem : getAll()) {
            JSONObject lineItemObj = new JSONObject();
            JSONObject productObj = new JSONObject();
            lineItemObj.put("id", lineItem.getId());
            lineItemObj.put("quantity", lineItem.getQuantity());
            productObj.put("productName", lineItem.getProduct().getName());
            productObj.put("defaultPrice", lineItem.getProduct().getDefaultPrice());
            productObj.put("defaultCurrency", lineItem.getProduct().getDefaultCurrency());
            productObj.put("productCategory", lineItem.getProduct().getProductCategory().getName());
            productObj.put("supplier", lineItem.getProduct().getSupplier().getName());
            lineItemObj.put("product", productObj);
            lineItemsOfOrder.add(lineItemObj);
            System.out.println("Json_file_done");
        }

        orders.put("firstOrder", order);
        order.put("lineItems", lineItemsOfOrder);

        try (FileWriter file = new FileWriter("src/main/resources/log/orders.txt")) {
            file.write(orders.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

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
