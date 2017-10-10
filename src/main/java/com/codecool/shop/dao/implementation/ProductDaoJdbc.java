package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/shop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static ProductDaoJdbc instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoJdbc() {
    }

    public static ProductDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductDaoJdbc();
        }
        return instance;
    }

    public void add(Product product) {
        String query = "INSERT INTO products (name, description, price, " +
                "currency, product_category_id, supplier_id) VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setFloat(3, product.getDefaultPrice());
            statement.setString(4, product.getDefaultCurrency().toString());
            statement.setString(5, product.getProductCategory().getName());
            statement.setString(6, product.getSupplier().getName());
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product find(int id) {
        String query = "SELECT * FROM products WHERE id = ?";
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int prodId = resultSet.getInt("product_category_id");
                ProductCategory cat = ProductCategoryDAOJdbc.getInstance().find(prodId);

                Product result = new Product(resultSet.getString("name"),
                        resultSet.getFloat("defaultCurrency"),
                        resultSet.getString("description"),
                        resultSet.getString("defaultCurrency"),
                        cat, resultSet.getString("supplier"));
                return result;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(int id) {
        String query = "DELETE * FROM products WHERE id = ?";
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Product> getAll() {
        String query = "SELECT * FROM products";
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                ProductCategory cat = ProductCategoryDAOJdbc.getInstance().find(resultSet.getInt("product_category_id"));
                Product result = new Product(resultSet.getString("name"),
                        resultSet.getFloat("defaultCurrency"),
                        resultSet.getString("description"),
                        resultSet.getString("defaultCurrency"),
                        cat, resultSet.getString("supplier"));
                products.add(result);
            }
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getBy(ProductCategory productCategory) {
        int id = productCategory.getId();
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE product_category_id = ?";
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProductCategory cat = ProductCategoryDAOJdbc.getInstance().find(resultSet.getInt("product_category_id"));
                Product result = new Product(resultSet.getString("name"),
                        resultSet.getFloat("defaultCurrency"),
                        resultSet.getString("description"),
                        resultSet.getString("defaultCurrency"),
                        cat, resultSet.getString("supplier"));
                products.add(result);
                System.out.println("Got all prods");
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getBy(Supplier supplier) {
        int id = supplier.getId();
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE supplier_id = ?";
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProductCategory cat = ProductCategoryDAOJdbc.getInstance().find(resultSet.getInt("product_category_id"));
                Product result = new Product(resultSet.getString("name"),
                        resultSet.getFloat("defaultCurrency"),
                        resultSet.getString("description"),
                        resultSet.getString("defaultCurrency"),
                        cat, resultSet.getString("supplier"));
                products.add(result);
                System.out.println("Got all products");
            }
            return products;
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
