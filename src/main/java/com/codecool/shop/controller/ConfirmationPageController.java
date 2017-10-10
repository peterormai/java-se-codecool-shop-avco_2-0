package com.codecool.shop.controller;

import com.codecool.shop.model.Order;
import spark.Request;
import spark.Response;

import java.io.IOException;
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
    public String render(Request req, Response res) throws IOException {
        Map<String, Object> params = new HashMap<>();
        System.out.println(params);

        Order orderDataStore = Order.getInstance();
        orderDataStore.JSONFileWrite();

        return renderTemplate(params, "confirmation");
    }
}
