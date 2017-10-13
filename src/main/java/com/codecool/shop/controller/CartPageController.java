package com.codecool.shop.controller;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.implementation.LineItemDaoJdbc;
import com.codecool.shop.model.LineItem;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CartPageController extends Controller {

    private static CartPageController cartPageController = null;
    private LineItemDao lineItemDao;

    private CartPageController(LineItemDao lineItemDao) {
        this.lineItemDao = lineItemDao;
    }

    public static CartPageController getInstance(LineItemDao lineItemDao) {
        if (cartPageController == null) {
            cartPageController = new CartPageController(lineItemDao);
        }
        return cartPageController;
    }

    @Override
    public String render(Request req, Response res) {
        Map<String, Object> params = new HashMap<>();

        if (req.queryParams("id") != null) {
            int id = Integer.parseInt(req.queryParams("id"));

            lineItemDao.removeLineItem(id);
        }

        if (lineItemDao.getAll().isEmpty()) {
            params.put("emptyCart", "Your cart is empty.");
        }

        params.put("lineItems", lineItemDao.getAll());
        params.put("totalPrice", lineItemDao.getTotalPrice());

        return renderTemplate(params, "cart");
    }

    public JSONObject renderChangeItemQuantity(Request req, Response res) {
        int id = Integer.parseInt(req.queryParams("id"));
        int quantity = Integer.parseInt(req.queryParams("quantity"));

        lineItemDao.changeItemQuantity(id, quantity);

        LineItem lineItem = lineItemDao.get(id);
        JSONObject obj = new JSONObject();

        float total = lineItemDao.getTotalPrice();
        obj.put("total", total);

        if (lineItem != null) {
            float value = lineItem.getProduct().getDefaultPrice() * quantity;
            obj.put("value", value);
        } else {
            obj.put("value", 0);
        }

        return obj;
    }
}
