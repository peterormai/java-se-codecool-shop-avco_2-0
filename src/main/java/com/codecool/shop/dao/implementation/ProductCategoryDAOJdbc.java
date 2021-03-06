package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ConnectionManager;
import com.codecool.shop.model.ProductCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDAOJdbc implements ProductCategoryDao {

    private static ProductCategoryDAOJdbc instance = null;

    private ProductCategoryDAOJdbc() {
    }

    public static ProductCategoryDAOJdbc getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDAOJdbc();
        }
        return instance;
    }

    public void add(ProductCategory category) {
        String query = "INSERT INTO productcategories (name, department, description) " +
                "VALUES(?,?,?);";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDepartment());
            statement.setString(3, category.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("The database is already filled with data");
            throw new IllegalArgumentException(e);
        } catch (NullPointerException ie) {
            throw new IllegalArgumentException("Added: null, Expected: ProductCategory");
        }
    }

    public ProductCategory find(int id) {
        String query = "SELECT * FROM productcategories WHERE id = ?;";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            ProductCategory category = null;
            while (resultSet.next()) {
                category = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
                category.setId(id);
            }
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int findIdByName(String name) {
        String query = "SELECT id FROM productcategories WHERE name = ?;";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void remove(int id) {
        String query = "DELETE FROM productcategories WHERE id = ?;";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("removed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ProductCategory> getAll() {
        String query = "SELECT * FROM productcategories;";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<ProductCategory> categories = new ArrayList<>();
            while (resultSet.next()) {
                ProductCategory category = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
                category.setId(resultSet.getInt("id"));
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return new ConnectionManager("src/main/resources/sql/config.txt").getConnection();
    }

    public void executeQueryWithNoReturnValue(String query) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
