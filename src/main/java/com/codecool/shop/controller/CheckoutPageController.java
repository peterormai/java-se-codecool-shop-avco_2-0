package com.codecool.shop.controller;

import com.codecool.shop.model.Checkout;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CheckoutPageController extends Controller {

    private static CheckoutPageController checkoutPageController = null;

    private CheckoutPageController() {
    }

    public static CheckoutPageController getInstance() {
        if (checkoutPageController == null) {
            checkoutPageController = new CheckoutPageController();
        }
        return checkoutPageController;
    }

    @Override
    public String render(Request req, Response res) {
        Map<String, String> params = new HashMap<>();
        if (Checkout.getCheckoutMap().size() != 0) {
            params = Checkout.getCheckoutMap();
            params.put("phoneNumber", params.get("phoneNumber").replace("-", ""));
            return renderTemplate(params, "editCheckout");
        }
        return renderTemplate(params, "checkout");
    }
}
