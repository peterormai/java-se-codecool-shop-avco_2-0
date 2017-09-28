package com.codecool.shop.controller;

import com.codecool.shop.model.Order;
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
        Order orderDataStore = Order.getInstance();

        if (req.queryParams("quantity") != null) {
            String check = req.queryParams("ic-id").charAt(0) + "";
            if (check.equals("#")) {
                orderDataStore.changeItemValue(req.queryParams("ic-id"), req.queryParams("quantity"));
            }
        }

        if (req.queryParams("id") != null) {
            int id = Integer.parseInt(req.queryParams("id"));
            orderDataStore.remove(id);
        }

        if (orderDataStore.getAll().isEmpty()) {
            return ProductPageController.getInstance().render(req, res);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("lineItems", orderDataStore.getAll());
        params.put("totalPrice", orderDataStore.getTotalPrice());
        return renderTemplate(params, "cart");
    }
}
