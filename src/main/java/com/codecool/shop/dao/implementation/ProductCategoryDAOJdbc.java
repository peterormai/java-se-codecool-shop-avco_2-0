package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDAOJdbc implements ProductCategoryDao{

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/shop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static ProductCategoryDAOJdbc instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDAOJdbc() {
    }

    public static ProductCategoryDAOJdbc getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDAOJdbc();
        }
        return instance;
    }

    public void add(ProductCategory category) {
        String query = "INSERT INTO productCategories (name, department, description) " +
                "VALUES(?,?,?)";
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDepartment());
            statement.setString(3, category.getDescription());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProductCategory find(int id) {
        String query = "SELECT * FROM productCategories WHERE id = ?";
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            ProductCategory category = null;
            while (resultSet.next()) {
                category = new ProductCategory(resultSet.getString("name"),
                        resultSet.getString("department"), resultSet.getString("description"));
                category.setId(id);
                List<Product> products = ProductDaoJdbc.getInstance().getBy(category);
                category.setProducts(new ArrayList<>(products));
            }
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(int id) {
        String query = "DELETE * FROM productCategories WHERE id = ?";
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeQuery();
            System.out.println("removed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ProductCategory> getAll() {
        String query = "SELECT * FROM productCategories";
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<ProductCategory> categories = new ArrayList<>();
            while (resultSet.next()) {
                ProductCategory category = new ProductCategory(resultSet.getString("name"),
                        resultSet.getString("department"), resultSet.getString("description"));
                category.setId(resultSet.getInt("id"));
                List<Product> products = ProductDaoJdbc.getInstance().getBy(category);
                category.setProducts(new ArrayList<>(products));
                categories.add(category);
            }
            return categories;
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