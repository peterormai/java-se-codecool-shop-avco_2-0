package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import com.codecool.shop.model.Checkout;
import com.codecool.shop.model.Product;
import spark.Request;
import spark.Response;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {

    public static String renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        OrderDaoMem cartDataStore = OrderDaoMem.getInstance();

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
        return renderTemplate(params, "product/index");
    }


    private static String renderTemplate(Map model, String template) {
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, template));
    }

    public static Object addNewItemToCart(Request req, Response res) {
        OrderDao orderDao = OrderDaoMem.getInstance();
        String itemId = req.params("id");
        Product product = ProductDaoMem.getInstance().find(Integer.parseInt(itemId));
        orderDao.add(product);
        return renderProducts(req, res);
    }

    public static String checkoutCart(Request req, Response res) {
        Map<List, Map> params = new HashMap<>();
        return renderTemplate(params, "product/checkout");
    }

}
