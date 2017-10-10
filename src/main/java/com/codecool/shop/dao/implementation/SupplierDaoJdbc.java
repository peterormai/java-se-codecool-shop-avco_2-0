package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/shop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static SupplierDaoJdbc instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoJdbc() {
    }

    public static SupplierDaoJdbc getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJdbc();
        }
        return instance;
    }

    public void add(Supplier supplier) {
        String query = "INSERT INTO suppliers (name, description) VALUES (?,?)";
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getDescription());
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Supplier find(int id) {
        String query = "SELECT * FROM suppliers WHERE id = ?";
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Supplier supplier = null;
            while (resultSet.next()) {
                supplier = new Supplier(resultSet.getString("name"),
                        resultSet.getString("description"));
                supplier.setId(resultSet.getInt("id"));
                List<Product> products = ProductDaoJdbc.getInstance().getBy(supplier);
                supplier.setProducts(new ArrayList<>(products));
            }
            return supplier;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(int id) {
        String query = "DELETE * FROM suppliers WHERE id = ?";
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeQuery();
            System.out.println("Deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Supplier> getAll() {
        String query = "SELECT * FROM suppliers";
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Supplier> suppliers = new ArrayList<>();
            while (resultSet.next()) {
                Supplier supplier = new Supplier(resultSet.getString("name"),
                        resultSet.getString("description"));
                supplier.setId(resultSet.getInt("id"));
                List<Product> products = ProductDaoJdbc.getInstance().getBy(supplier);
                supplier.setProducts(new ArrayList<>(products));
                suppliers.add(supplier);
            }
            return suppliers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    private void executeQueryWithNoReturnValue(String query) {
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
