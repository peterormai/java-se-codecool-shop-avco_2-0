package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.OrderDaoMem;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CartPageController extends Controller {

    private static CartPageController cartPageController = null;

    private CartPageController(){}

    public static CartPageController getInstance() {
        if (cartPageController == null) {
            cartPageController = new CartPageController();
        }
        return cartPageController;
    }

    @Override
    public String render(Request req, Response res) {
        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();
        Map<String, Object> params = new HashMap<>();
        params.put("lineItems", orderDataStore.getAll());
        params.put("totalPrice", orderDataStore.getTotalPrice());
        return renderTemplate(params, "cart");
    }
}
