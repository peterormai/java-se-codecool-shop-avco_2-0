package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import com.codecool.shop.model.Checkout;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
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
        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        Map<String, Object> params = new HashMap<>();
        int id = 1;
        params.put("supplier", "notRelevant");
        params.put("type", "category");
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(id)));
        params.put("category", productCategoryDataStore.find(id));
        params.put("suppliers", supplierDataStore.getAll());
        params.put("categories", productCategoryDataStore.getAll());
        params.put("numberOfItems", orderDataStore.numberOfLineItems());

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
        OrderDao orderDao = OrderDaoMem.getInstance();
        String itemId = req.params("id");
        Product product = ProductDaoMem.getInstance().find(Integer.parseInt(itemId));
        orderDao.add(product);
        return renderProducts(req, res);
    }

    public static Object reviewCart(Request req, Response res) {
        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();
        Map<String, Object> params = new HashMap<>();
        params.put("lineItems", orderDataStore.getAll());
        params.put("totalPrice", orderDataStore.getTotalPrice());
        return renderTemplate(params, "cart");
    }
    public static String checkoutCart(Request req, Response res) {
        Map<List, Map> params = new HashMap<>();
        return renderTemplate(params, "product/checkout");
    }

    public static String payTheOrder(Request req, Response res){
        Map<String, String> params = new HashMap<>();
        params.put("name",req.queryParams("name"));
        params.put("email",req.queryParams("email"));
        params.put("phoneNumber",req.queryParams("phoneNumber"));
        params.put("billingCountry",req.queryParams("billingCountry"));
        params.put("billingCity",req.queryParams("billingCity"));
        params.put("billingZipCode",req.queryParams("billingZipCode"));
        params.put("billingAddress",req.queryParams("billingAddress"));
        params.put("shippingCountry",req.queryParams("shippingCountry"));
        params.put("shippingCity",req.queryParams("shippingCity"));
        params.put("shippingZipCode",req.queryParams("shippingZipCode"));
        params.put("shippingAddress",req.queryParams("shippingAddress"));
        Checkout.getInstance(params);
        return renderTemplate(params, "product/pay");
    }

}
