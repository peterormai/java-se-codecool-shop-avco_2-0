import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import com.codecool.shop.controller.*;
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
        get("/filter", ProductPageController.getInstance()::render);
        get("/review-cart", CartPageController.getInstance()::render);
        post("/review-cart", CartPageController.getInstance()::render);
        get("/checkout", CheckoutPageController.getInstance()::render);
        get("/payment", PaymentPageController.getInstance()::render);
        post("/payment", PaymentPageController.getInstance()::render);
        get("/confirmation", ConfirmationPageController.getInstance()::render);
        post("/confirmation", ConfirmationPageController.getInstance()::render);

        // Always add generic   routes to the end
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
        Supplier identityTChan = new Supplier("Identity-t Chan", "The biggest copy factory");
        supplierDataStore.add(identityTChan);

        //setting up a new product category
        ProductCategory idCard = new ProductCategory("Identity Card", "Identity Card", "It's looks like the original hungarian Identity card.");
        productCategoryDataStore.add(idCard);

        ProductCategory addressCard = new ProductCategory("Address Card", "Address Card", "This card contain official address");
        productCategoryDataStore.add(addressCard);

        ProductCategory driverLicence = new ProductCategory("Driver Licence", "Driver Licence", "With this awesome card, you can driving in Hungary");
        productCategoryDataStore.add(driverLicence);

        ProductCategory erettsegi = new ProductCategory("Érettségi", "Document", "This document justifies, that you finished the High School ");
        productCategoryDataStore.add(erettsegi);

        ProductCategory okj = new ProductCategory("OKJ", "Document", "This document justifies, you are good enough in one profession");
        productCategoryDataStore.add(okj);

        ProductCategory diploma = new ProductCategory("Diploma", "Document", "This document justifies, you are master in one profession");
        productCategoryDataStore.add(diploma);

        ProductCategory language = new ProductCategory("Language Exam", "Document", "This document justifies, you are good enough in a language");
        productCategoryDataStore.add(language);

        ProductCategory bkk = new ProductCategory("BKK", "Ticket", "With this ticket you can traveling in Budapest ");
        productCategoryDataStore.add(bkk);

        ProductCategory pack = new ProductCategory("Pack", "Document", "You can buy some document in pack, to be sure, that will be work well ");
        productCategoryDataStore.add(pack);

        ProductCategory franchise = new ProductCategory("Franchaise", "Printer", "With this special Printer you can make your own document");
        productCategoryDataStore.add(franchise);


        //setting up products and printing it
        productDataStore.add(new Product("Identity Card (Womean)", 41, "USD", "If you are don't like yourself, than you can be anybody", idCard, felcsutCompany));
        productDataStore.add(new Product("Identity Card (Man)", 42, "USD", "If you are ugly, or maybe just you can not leave your country, with new ID card you can do that ", idCard, felcsutCompany));

        productDataStore.add(new Product("Address Card", 180, "USD", "You can live everywhere with this card", addressCard, felcsutCompany));
        productDataStore.add(new Product("Address Card (Family pack)", 180, "USD", "This pack include 4 address card what you can shear with your family or friends", addressCard, felcsutCompany));

        productDataStore.add(new Product("Driver Licence", 230, "USD", "It's just looks like driver licence, without category ", driverLicence, demcsakZssss));
        productDataStore.add(new Product("B category - Driver Licence ", 580, "USD", "With this driver licence you can use you slovakian car in the hungarian roads", driverLicence, demcsakZssss));
        productDataStore.add(new Product("C category - Driver Licence ", 580, "USD", "With this driver licence is much easier to take the refugees in the country, and you should not  take car for police ", driverLicence, demcsakZssss));

        productDataStore.add(new Product("Érettségi", 400, "USD", "With this amazing document much easier get a job at awesome McDonald's, TESCO, SPAR, or at your friends ", erettsegi, schmitTpal));

        productDataStore.add(new Product("Idegenvezető - OKJ", 450, "USD", "If you have this document you can talk with tourists about nice building. They are going to pay for that  ", okj, schmitTpal));
        productDataStore.add(new Product("Ingatlankezelő - OKJ", 430, "USD", "If will be Ingatlankezelő, you will be the boss of many realtor ", okj, schmitTpal));

        productDataStore.add(new Product("Diploma", 4500, "USD", "It's just a simple diploma without university, if you boss will searching for your name ate the university he will be know it's a fail diploma", diploma, schmitTpal));
        productDataStore.add(new Product("Közgazdász", 6300, "USD", "Gazdaságtudományi Műszaki és Gazdasági-és Társadalomtudományi Kar Gazdálkodási - Diploma", diploma, schmitTpal));
        productDataStore.add(new Product("Logopédia szakos tanár és pszichopedagógia szakos terapeauta - Diploma", 6250, "USD", "If will be Ingatlankezelő, you will be the boss of many realtor ", diploma, schmitTpal));
        productDataStore.add(new Product("Építészmérnök", 7200, "HUF", " Széchenyi István Egyetem Műszaki Tudományi Kar ", diploma, schmitTpal));

        productDataStore.add(new Product("English - Language Exam", 200, "USD", "With this document you will be english language ninja (on the paper) ", language, schmitTpal));
        productDataStore.add(new Product("Germany - Language Exam", 225, "USD", "With this document you will be germany language ninja (on the paper) ", language, schmitTpal));
        productDataStore.add(new Product("French - Language Exam", 230, "USD", "With this document you will be french language ninja (on the paper) ", language, schmitTpal));
        productDataStore.add(new Product("Spain - Language Exam", 230, "USD", "With this document you will be spain language ninja (on the paper) ", language, schmitTpal));

        productDataStore.add(new Product("Digital ticket - 30 day", 10, "USD", "With this printer you will able to made your own busniess, but 20% of your proift will be ours", bkk, tSystem));
        productDataStore.add(new Product("Paper ticket - 30 day ", 15, "USD", "With this printer you will able to made your own busniess, but 20% of your proift will be ours", bkk, tSystem));
        productDataStore.add(new Product("Paper ticket - 1 way ", 1, "USD", "With this printer you will able to made your own busniess, but 20% of your proift will be ours", bkk, tSystem));


        productDataStore.add(new Product("Identity Card + Address Card ", 80, "USD", "Your identity will be more real ", pack, schmitTpal));

        productDataStore.add(new Product("Printer + shipping to you home", 40000, "USD", "With this printer you will able to made your own busniess, but 40% of your proift will be ours ", franchise, identityTChan));
        productDataStore.add(new Product("Printer + shipping to border of your country ", 35000, "USD", "With this printer you will able to made your own busniess, but 20% of your proift will be ours", franchise, identityTChan));

    }


}
