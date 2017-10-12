import com.codecool.shop.controller.*;
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
        try {

            CreateDataForDatabase.createData();
        } catch (IllegalArgumentException ie) {

        }


        // Always start with more specific routes
        get("/add-to-cart/:id", ProductPageController.getInstance()::addNewItemToCart);
        get("/filter", ProductPageController.getInstance()::render);
        get("/review-cart", CartPageController.getInstance()::render);
        put("/review-cart", CartPageController.getInstance()::renderChangeItemQuantity);
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

}



