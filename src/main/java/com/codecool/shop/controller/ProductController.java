package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        int id = 1;
        Map params = new HashMap<>();
        params.put("supplier", "notRelevant");
        params.put("type", "category");
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(id)));
        params.put("category", productCategoryDataStore.find(id));
        params.put("suppliers", supplierDataStore.getAll());

        if (req.queryParams("type") != null) {
            id = Integer.parseInt(req.queryParams("id"));
            params.put("type", "supplier");
            params.put("supplier", supplierDataStore.find(id));
            params.put("products", productDataStore.getBy(supplierDataStore.find(id)));
        }
        return new ModelAndView(params, "product/index");
    }
}
