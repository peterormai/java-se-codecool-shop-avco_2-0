package com.codecool.shop.controller;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import com.codecool.shop.model.ProductCategory;
import spark.Request;
import spark.Response;

import javax.net.ssl.SSLContext;
import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.Map;

public class ProductPageController extends Controller {

    private static ProductPageController productPageController = null;

    private ProductPageController() {
    }

    public static ProductPageController getInstance() {
        if (productPageController == null) {
            productPageController = new ProductPageController();
        }
        return productPageController;
    }

    public String render(Request req, Response res) {
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDAOJdbc.getInstance();
        LineItemDao orderDataStore = LineItemDaoJdbc.getInstance();
        SupplierDao supplierDataStore = SupplierDaoJdbc.getInstance();

        Map<String, Object> params = new HashMap<>();
        int id = 1;
        params.put("supplier", "notRelevant");
        params.put("type", "category");
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(id)));
        params.put("category", productCategoryDataStore.find(id));
        params.put("selected", productCategoryDataStore.find(id));
        params.put("suppliers", supplierDataStore.getAll());
        params.put("categories", productCategoryDataStore.getAll());
        params.put("numberOfItems", orderDataStore.getNumberOfItem());
        if (req.queryParams("selected") != null) {
            params.put("category", req.queryParams("selected"));
            params.put("selected", req.queryParams("selected"));
            System.out.println(params.get("selected"));
        }

        if (req.queryParams("type") != null) {
            if (req.queryParams("type").equals("supplier")) {
                id = Integer.parseInt(req.queryParams("id"));
                params.put("type", "supplier");
                params.put("supplier", supplierDataStore.find(id));
                params.put("products", productDataStore.getBy(supplierDataStore.find(id)));
            } else if (req.queryParams("type").equals("category")) {
                int categoryID = Integer.parseInt(req.queryParams("id"));
                params.put("category", productCategoryDataStore.find(categoryID));
                params.put("products", productDataStore.getBy(productCategoryDataStore.find(categoryID)));
            }
            params.put("selected", req.queryParams("name"));
        }
        return renderTemplate(params, "index");
    }

    public Object addNewItemToCart(Request req, Response res) {
        int itemId =Integer.parseInt(req.params("id"));
        Product product = ProductDaoJdbc.getInstance().find(itemId+1);
        LineItemDaoJdbc.getInstance().add(product);
        Map<String, Object> params = new HashMap<>();
        params.put("numberOfItems", LineItemDaoJdbc.getInstance().getNumberOfItem());
        return renderTemplate(params, "cartButton");
    }
}
