package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.ConnectionManager;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

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
                "currency, picture, product_category_id, supplier_id) VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setFloat(3, product.getDefaultPrice());
            statement.setString(4, product.getDefaultCurrency().toString());
            statement.setString(5, product.getPictureRoute());
            statement.setInt(6, ProductCategoryDAOJdbc.getInstance().findIdByName(product.getProductCategory().getName()));
            statement.setInt(7, SupplierDaoJdbc.getInstance().findIdByName(product.getSupplier().getName()));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("The database is already filled with data ");
            throw new IllegalArgumentException(e);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Added: null, Expected: Product");
        }
    }

    public Product find(int id) {
        String query = "SELECT * FROM products WHERE id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int prodId = resultSet.getInt("product_category_id");
                ProductCategory cat = ProductCategoryDAOJdbc.getInstance().find(prodId);
                int suppId = resultSet.getInt("supplier_id");
                Supplier supp = SupplierDaoJdbc.getInstance().find(suppId);

                Product product = getResult(resultSet, cat, supp);
                product.setId(resultSet.getInt("id"));
                return product;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(int id) {
        String query = "DELETE FROM products WHERE id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAll() {
        String query = "SELECT * FROM products";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                ProductCategory cat = ProductCategoryDAOJdbc.getInstance().find(resultSet.getInt("product_category_id"));
                int suppId = getSuppId(resultSet);
                Supplier supp = SupplierDaoJdbc.getInstance().find(suppId);
                Product result = getResult(resultSet, cat, supp);
                result.setId(resultSet.getInt("id"));
                products.add(result);
            }
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getSuppId(ResultSet resultSet) throws SQLException {
        return resultSet.getInt("supplier_id");
    }

    public List<Product> getBy(ProductCategory productCategory) {
        int id = productCategory.getId();
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE product_category_id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProductCategory cat = ProductCategoryDAOJdbc.getInstance().find(resultSet.getInt("product_category_id"));
                int suppId = resultSet.getInt("supplier_id");
                Supplier supp = SupplierDaoJdbc.getInstance().find(suppId);
                Product result = getResult(resultSet, cat, supp);
                result.setId(resultSet.getInt("id"));
                products.add(result);

            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Product getResult(ResultSet resultSet, ProductCategory cat, Supplier supp) throws SQLException {
        return new Product(resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("currency"),
                resultSet.getString("description"),
                cat, supp,
                resultSet.getString("picture"));
    }

    public List<Product> getBy(Supplier supplier) {
        int id = supplier.getId();
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE supplier_id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProductCategory cat = ProductCategoryDAOJdbc.getInstance().find(resultSet.getInt("product_category_id"));
                int suppId = resultSet.getInt("supplier_id");
                Supplier supp = SupplierDaoJdbc.getInstance().find(suppId);
                Product result = new Product(resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        cat, supp,
                        resultSet.getString("picture"));
                result.setId(resultSet.getInt("id"));
                products.add(result);

            }
            return products;
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
