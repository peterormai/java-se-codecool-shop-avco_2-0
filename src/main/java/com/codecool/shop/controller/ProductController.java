package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import com.codecool.shop.model.Product;
import spark.Request;
import spark.Response;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProductController {

    public static String renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        CartDaoMem cartDataStore = CartDaoMem.getInstance();

        Map<String, Object> params = new HashMap<>();
        params.put("categories", productCategoryDataStore.getAll());
        params.put("numberOfItems", cartDataStore.numberOfLineItems());
        if (req.params("categoryID") != null) {
            int categoryID = Integer.parseInt(req.params("categoryID"));
            params.put("category", productCategoryDataStore.find(categoryID));
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(categoryID)));

        } else {
            params.put("category", productCategoryDataStore.find(1));
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        }
        if ("true".equals(req.queryParams("ic-request"))) {
            return renderTemplate(params, "content");
        }
        return renderTemplate(params, "product/index");
    }

    private static String renderTemplate(Map model, String template) {
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, template));
    }

    public static Object addNewItemToCart(Request req, Response res) {
        CartDao cartDao = CartDaoMem.getInstance();
        String itemId = req.params("id");
        Product product = ProductDaoMem.getInstance().find(Integer.parseInt(itemId));
        cartDao.add(product);
        return renderProducts(req, res);
    }

    public static Object reviewCart(Request req, Response res) {
        CartDaoMem cartDataStore = CartDaoMem.getInstance();
        Map<String, Object> params = new HashMap<>();
        params.put("lineItems", cartDataStore.getAll());
        params.put("totalPrice", cartDataStore.getTotalPrice());
        return renderTemplate(params, "cart");
    }
}
