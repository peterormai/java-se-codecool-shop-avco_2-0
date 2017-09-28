package com.codecool.shop.controller;

import com.codecool.shop.model.Checkout;
import com.codecool.shop.model.Order;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PaymentPageController extends Controller {

    private static PaymentPageController paymentPageController = null;

    private PaymentPageController() {
    }

    public static PaymentPageController getInstance() {
        if (paymentPageController == null) {
            paymentPageController = new PaymentPageController();
        }
        return paymentPageController;
    }

    @Override
    public String render(Request req, Response res) {
        Map<String, String> params = new HashMap<>();
        Order orderDataStore = Order.getInstance();
        orderDataStore.removeAll();
        return renderTemplate(params, "payment");
    }
}
