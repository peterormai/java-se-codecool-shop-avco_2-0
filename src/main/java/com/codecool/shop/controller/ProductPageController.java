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
    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;
    private LineItemDao lineItemDao;
    private SupplierDao supplierDao;

    private ProductPageController(ProductDao productDao, ProductCategoryDao productCategoryDao, LineItemDao lineItemDao, SupplierDao supplierDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.lineItemDao = lineItemDao;
        this.supplierDao = supplierDao;
    }

    public static ProductPageController getInstance(ProductDao productDao, ProductCategoryDao productCategoryDao, LineItemDao lineItemDao, SupplierDao supplierDao) {
        if (productPageController == null) {
            productPageController = new ProductPageController(productDao,productCategoryDao,lineItemDao,supplierDao);
        }
        return productPageController;
    }

    public String render(Request req, Response res) {
        ProductDao productDao = this.productDao;
        ProductCategoryDao productCategoryDao = this.productCategoryDao;
        LineItemDao lineItemDao = this.lineItemDao;
        SupplierDao supplierDao = this.supplierDao;

        Map<String, Object> params = new HashMap<>();
        int id = 1;
        params.put("supplier", "notRelevant");
        params.put("type", "category");
        params.put("products", productDao.getBy(productCategoryDao.find(id)));
        params.put("category", productCategoryDao.find(id));
        params.put("selected", productCategoryDao.find(id));
        params.put("suppliers", supplierDao.getAll());
        params.put("categories", productCategoryDao.getAll());
        params.put("numberOfItems", lineItemDao.getNumberOfItem());
        if (req.queryParams("selected") != null) {
            params.put("category", req.queryParams("selected"));
            params.put("selected", req.queryParams("selected"));
            System.out.println(params.get("selected"));
        }

        if (req.queryParams("type") != null) {
            if (req.queryParams("type").equals("supplier")) {
                id = Integer.parseInt(req.queryParams("id"));
                params.put("type", "supplier");
                params.put("supplier", supplierDao.find(id));
                params.put("products", productDao.getBy(supplierDao.find(id)));
            } else if (req.queryParams("type").equals("category")) {
                int categoryID = Integer.parseInt(req.queryParams("id"));
                params.put("category", productCategoryDao.find(categoryID));
                params.put("products", productDao.getBy(productCategoryDao.find(categoryID)));
            }
            params.put("selected", req.queryParams("name"));
        }
        return renderTemplate(params, "index");
    }

    public Object addNewItemToCart(Request req, Response res ) {
        int itemId =Integer.parseInt(req.params("id"));
        Product product = productDao.find(itemId+1);
        this.lineItemDao.add(product);
        Map<String, Object> params = new HashMap<>();
        params.put("numberOfItems", lineItemDao.getNumberOfItem());
        return renderTemplate(params, "cartButton");
    }
}
