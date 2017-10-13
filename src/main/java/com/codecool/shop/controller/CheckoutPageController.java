package com.codecool.shop.controller;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.Checkout;
import com.codecool.shop.model.OrderStatus;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CheckoutPageController extends Controller {

    private static CheckoutPageController checkoutPageController = null;
    private LineItemDao lineItemDao;


    private CheckoutPageController(LineItemDao lineItemDao) {

        this.lineItemDao = lineItemDao;

    }

    public static CheckoutPageController getInstance(LineItemDao lineItemDao) {
        if (checkoutPageController == null) {
            checkoutPageController = new CheckoutPageController(lineItemDao);
        }
        return checkoutPageController;
    }

    @Override
    public String render(Request req, Response res) {
        Map<String, String> params = new HashMap<>();
        LineItemDao lineItemDao = this.lineItemDao;
        lineItemDao.getStatus();
        if (lineItemDao.getNumberOfItem() == 0) {
            res.redirect("/");
        }

        if (lineItemDao.getStatus() == OrderStatus.CHECKEDOUT) {
            params = Checkout.getCheckoutMap();
            params.put("phoneNumber", params.get("phoneNumber").replace("-", ""));
            return renderTemplate(params, "editCheckout");
        }

        return renderTemplate(params, "checkout");
    }
}
