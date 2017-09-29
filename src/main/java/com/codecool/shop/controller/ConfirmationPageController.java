package com.codecool.shop.controller;

import com.codecool.shop.model.Checkout;
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
        Map<String, String> params = new HashMap<>();

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

        params = Checkout.getCheckoutMap();
        return renderTemplate(params, "confirmation");
    }
}
