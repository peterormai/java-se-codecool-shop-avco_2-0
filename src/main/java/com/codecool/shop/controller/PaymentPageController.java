package com.codecool.shop.controller;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.implementation.LineItemDaoJdbc;
import com.codecool.shop.model.Checkout;
import com.codecool.shop.model.OrderStatus;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PaymentPageController extends Controller {

    private static PaymentPageController paymentPageController = null;
    private LineItemDao lineItemDao;

    private PaymentPageController(LineItemDao lineItemDao) {
        this.lineItemDao = lineItemDao;
    }

    public static PaymentPageController getInstance(LineItemDao lineItemDao) {
        if (paymentPageController == null) {
            paymentPageController = new PaymentPageController(lineItemDao);
        }
        return paymentPageController;
    }

    @Override
    public String render(Request req, Response res) {

        Map<String, Object> params = new HashMap<>();
        LineItemDao lineItemDao = LineItemDaoJdbc.getInstance();

        if (req.queryParams("checkout") == null) {
            res.redirect("/");
            return "";
        }
        if (lineItemDao.getNumberOfItem() > 0 && req.queryParams("checkout").equals("done") && lineItemDao.getStatus() != OrderStatus.CHECKEDOUT) {
            lineItemDao.setStatus(OrderStatus.CHECKEDOUT);
        } else if (!req.queryParams("checkout").equals("done")){
            res.redirect("/");
            return "";
        }

        params.put("firstName", req.queryParams("firstName"));
        params.put("lastName", req.queryParams("lastName"));
        params.put("email", req.queryParams("email"));
        params.put("phoneNumber", req.queryParams("phoneNumber"));
        params.put("billingCountry", req.queryParams("billingCountry"));
        params.put("billingCity", req.queryParams("billingCity"));
        params.put("billingZipCode", req.queryParams("billingZipCode"));
        params.put("billingAddress", req.queryParams("billingAddress"));
        params.put("shippingCountry", req.queryParams("shippingCountry"));
        params.put("shippingCity", req.queryParams("shippingCity"));
        params.put("shippingZipCode", req.queryParams("shippingZipCode"));
        params.put("shippingAddress", req.queryParams("shippingAddress"));

        Checkout.getInstance(params);
        Checkout.getInstance(params).setFirstName(req.queryParams("firstName"));
        Checkout.getInstance(params).setLastName(req.queryParams("lastName"));
        Checkout.getInstance(params).setEmail(req.queryParams("email"));
        Checkout.getInstance(params).setPhoneNumber(req.queryParams("phoneNumber"));
        Checkout.getInstance(params).setBillingCountry(req.queryParams("billingCountry"));
        Checkout.getInstance(params).setBillingCity(req.queryParams("billingCity"));
        Checkout.getInstance(params).setBillingZipCode(req.queryParams("billingZipCode"));
        Checkout.getInstance(params).setBillingAddress(req.queryParams("billingAddress"));
        Checkout.getInstance(params).setShippingCountry(req.queryParams("shippingCountry"));
        Checkout.getInstance(params).setShippingCity(req.queryParams("shippingCity"));
        Checkout.getInstance(params).setShippingZipCode(req.queryParams("shippingZipCode"));
        Checkout.getInstance(params).setShippingAddress(req.queryParams("shippingAddress"));

        params.put("totalPrice", lineItemDao.getTotalPrice());
        return renderTemplate(params, "payment");
    }
}
