package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import spark.Request;
import spark.Response;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class ProductController {

    public static String renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        CartDaoMem cartDataStore = CartDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        Map<String, Object> params = new HashMap<>();
        int id = 1;
        params.put("supplier", "notRelevant");
        params.put("type", "category");
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(id)));
        params.put("category", productCategoryDataStore.find(id));
        params.put("suppliers", supplierDataStore.getAll());
        params.put("categories", productCategoryDataStore.getAll());
        params.put("numberOfItems", cartDataStore.numberOfLineItems());

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
        res.redirect("/");
        return null;
    }
}
