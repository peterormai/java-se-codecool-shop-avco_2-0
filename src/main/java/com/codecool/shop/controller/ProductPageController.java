package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;

import spark.Request;
import spark.Response;

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
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        Order orderDataStore = Order.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        Map<String, Object> params = new HashMap<>();
        int id = 1;
        params.put("supplier", "notRelevant");
        params.put("type", "category");
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(id)));
        params.put("category", productCategoryDataStore.find(id));
        params.put("selected", productCategoryDataStore.find(id));
        params.put("suppliers", supplierDataStore.getAll());
        params.put("categories", productCategoryDataStore.getAll());
        params.put("numberOfItems", orderDataStore.numberOfLineItems());
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
        if ("true".equals(req.queryParams("ic-request"))) {
            return renderTemplate(params, "content");
        }
        return renderTemplate(params, "content");
    }

    public Object addNewItemToCart(Request req, Response res) {
        Order order = Order.getInstance();
        String itemId = req.params("id");
        Product product = ProductDaoMem.getInstance().find(Integer.parseInt(itemId));
        order.add(product);
        Map<String, Object> params = new HashMap<>();
        params.put("numberOfItems", order.numberOfLineItems());
        return renderTemplate(params, "cartButton");
    }
}
