import com.codecool.shop.controller.*;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.CreateDataForDatabase;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        //Create data in database
        String LOGIC = "JDBC";
        if (LOGIC == "MEM") {

            CreateDataForDatabase.createDaoMem();
            ProductDao productDao = ProductDaoMem.getInstance();
            ProductCategoryDao productCategoryDao = ProductCategoryDaoMem.getInstance();
            LineItemDao lineItemDao = LineItemDaoMem.getInstance();
            SupplierDao supplierDao = SupplierDaoMem.getInstance();
            renderRoute(productDao, productCategoryDao, lineItemDao, supplierDao);
        } else if (LOGIC == "JDBC") {
            try {

                CreateDataForDatabase.createData();
            } catch (Exception e) {

            }
            ProductDao productDao = ProductDaoJdbc.getInstance();
            ProductCategoryDao productCategoryDao = ProductCategoryDAOJdbc.getInstance();
            LineItemDao lineItemDao = LineItemDaoJdbc.getInstance();
            SupplierDao supplierDao = SupplierDaoJdbc.getInstance();
            renderRoute(productDao, productCategoryDao, lineItemDao, supplierDao);
        }
    }

    private static void renderRoute(ProductDao productDao, ProductCategoryDao productCategoryDao, LineItemDao lineItemDao, SupplierDao supplierDao) {
        // Always start with more specific routes
        get("/add-to-cart/:id", ProductPageController.getInstance(productDao, productCategoryDao, lineItemDao, supplierDao)::addNewItemToCart);
        get("/filter", ProductPageController.getInstance(productDao, productCategoryDao, lineItemDao, supplierDao)::render);
        get("/review-cart", CartPageController.getInstance(lineItemDao)::render);
        put("/review-cart", CartPageController.getInstance(lineItemDao)::renderChangeItemQuantity);
        get("/checkout", CheckoutPageController.getInstance(lineItemDao)::render);
        get("/payment", PaymentPageController.getInstance(lineItemDao)::render);
        post("/payment", PaymentPageController.getInstance(lineItemDao)::render);
        get("/confirmation", ConfirmationPageController.getInstance(lineItemDao)::render);
        post("/confirmation", ConfirmationPageController.getInstance(lineItemDao)::render);

        // Always add generic   routes to the end
        get("/index", ProductPageController.getInstance(productDao, productCategoryDao, lineItemDao, supplierDao)::render);
        get("/", ProductPageController.getInstance(productDao, productCategoryDao, lineItemDao, supplierDao)::render);

        enableDebugScreen();
    }

}



