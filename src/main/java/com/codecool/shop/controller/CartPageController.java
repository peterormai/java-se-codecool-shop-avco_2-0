package com.codecool.shop.controller;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.implementation.LineItemDaoJdbc;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CartPageController extends Controller {

    private static CartPageController cartPageController = null;

    private CartPageController() {
    }

    public static CartPageController getInstance() {
        if (cartPageController == null) {
            cartPageController = new CartPageController();
        }
        return cartPageController;
    }

    @Override
    public String render(Request req, Response res) {
        LineItemDao orderDataStore = LineItemDaoJdbc.getInstance();
        Map<String, Object> params = new HashMap<>();

        if (req.queryParams("id") != null) {
            int id = Integer.parseInt(req.queryParams("id"));

            LineItemDaoJdbc.getInstance().removeLineItem(id);
        }

        if (orderDataStore.getAll().isEmpty()) {
            params.put("emptyCart", "Your cart is empty.");
        }

        params.put("lineItems", orderDataStore.getAll());
        params.put("totalPrice", orderDataStore.getTotalPrice());
        return renderTemplate(params, "cart");
    }

    public JSONObject renderChangeItemQuantity(Request req, Response res) {
        LineItemDao lineItemDao = LineItemDaoJdbc.getInstance();
        int id = Integer.parseInt(req.queryParams("id"));
        lineItemDao.changeItemQuantity(id,Integer.parseInt(req.queryParams("quantity")));
        LineItem lineItem = LineItemDaoJdbc.getInstance().get(id);
        JSONObject obj = new JSONObject();
        float total = lineItemDao.getTotalPrice();
        obj.put("total", total);
        if (lineItem != null) {
            float value = lineItemDao.getTotalPrice();
            obj.put("value", value);
        } else {
            obj.put("value", 0);
        }

        return obj;
    }
}
