package com.codecool.shop.controller;

import com.codecool.shop.model.Checkout;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.OrderStatus;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ConfirmationPageController extends Controller {

    private static ConfirmationPageController confirmationPageController = null;

    private ConfirmationPageController() {
    }

    public static ConfirmationPageController getInstance() {
        if (confirmationPageController == null) {
            confirmationPageController = new ConfirmationPageController();
        }
        return confirmationPageController;
    }

    @Override
    public String render(Request req, Response res) {
        Map<String, Object> params = new HashMap<>();
        System.out.println(params);
        return renderTemplate(params, "confirmation");
    }
}
