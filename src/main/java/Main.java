import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import com.codecool.shop.controller.CartPageController;
import com.codecool.shop.controller.CheckoutPageController;
import com.codecool.shop.controller.PayPageController;
import com.codecool.shop.controller.ProductPageController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.*;

public class Main {

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // populate some data for the memory storage
        populateData();

        // Always start with more specific routes
        get("/add-to-cart/:id", ProductPageController.getInstance()::addNewItemToCart);
        get("/review-cart", CartPageController.getInstance()::render);
        get("/checkout", CheckoutPageController.getInstance()::render);
        post("/review-cart", CartPageController.getInstance()::render);
        post("/pay", PayPageController.getInstance()::render);
        get("/filter", ProductPageController.getInstance()::render);

        // Always add generic routes to the end
        get("/index", ProductPageController.getInstance()::render);
        get("/", ProductPageController.getInstance()::render);

        enableDebugScreen();
    }


    public static void populateData() {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier tSystem = new Supplier("t-System", "Digital content and services");
        supplierDataStore.add(tSystem);
        Supplier schmitTpal = new Supplier("SchmitTpál.zrt", "Corrupters");
        supplierDataStore.add(schmitTpal);
        Supplier demcsakZssss = new Supplier("DemcsákZssss.rt", "The alcohol is bad, okeeeee?");
        supplierDataStore.add(demcsakZssss);
        Supplier felcsutCompany = new Supplier("Felcsút Company", "We have enough money for everything");
        supplierDataStore.add(felcsutCompany);

        //setting up a new product category
        ProductCategory idCard = new ProductCategory("Identity Card", "Identity Card", "It's looks like the original hungarian Identity card.");
        productCategoryDataStore.add(idCard);

        ProductCategory addressCard = new ProductCategory("Address Card", "Address Card", "This card contain official address");
        productCategoryDataStore.add(addressCard);

        ProductCategory driverLicence = new ProductCategory("Driver Licence", "Driver Licence", "With this awesome card, you can driving in Hungary");
        productCategoryDataStore.add(driverLicence);

        ProductCategory erettsegi = new ProductCategory("Érettségi", "Document", "This document justifies, that you finished the High School ");
        productCategoryDataStore.add(erettsegi);

        ProductCategory okj = new ProductCategory("OKJ", "Document", "This document justifies, you are good enough in one profession  ");
        productCategoryDataStore.add(okj);

        ProductCategory diploma = new ProductCategory("Diploma", "Document", "This document justifies, you are master in one profession  ");
        productCategoryDataStore.add(diploma);

        ProductCategory language = new ProductCategory("Language Exam", "Document", "This document justifies, you are good enough in a language  ");
        productCategoryDataStore.add(language);

        ProductCategory pack = new ProductCategory("Pack", "Document", "You can buy some document in pack, to be sure, that will be work well ");
        productCategoryDataStore.add(pack);

        ProductCategory frenchaise = new ProductCategory("Frenchaise", "Printer", "With this special Printer you can make your own document");
        productCategoryDataStore.add(frenchaise);


        //setting up products and printing it
        productDataStore.add(new Product("Identity Card (Womean)", 10001, "HUF", "If you are don't like yourself, than you can be anybody", idCard, felcsutCompany));
        productDataStore.add(new Product("Identity Card (Man)", 10002, "HUF", "If you are ugly, or maybe just you can not leave your country, with new ID card you can do that ", idCard, felcsutCompany));
        productDataStore.add(new Product("Address Card", 15000, "HUF", "You can live everywhere with this card, where the location number under then 2 million", idCard, felcsutCompany));

        productDataStore.add(new Product("Apple iPhone 8", 799, "USD",
                "iPhone 8 introduces an all‑new glass design. The world’s most popular camera, now even better. " +
                        "The smartest, most powerful chip ever in a smartphone. Wireless charging that’s truly effortless. " +
                        "And augmented reality experiences never before possible. " +
                        "iPhone 8. A new generation of iPhone.", addressCard, demcsakZssss));
    }


}
