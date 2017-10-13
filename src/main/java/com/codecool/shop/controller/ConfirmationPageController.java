package com.codecool.shop.controller;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.Checkout;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.OrderStatus;
import spark.Request;
import spark.Response;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConfirmationPageController extends Controller {

    private LineItemDao lineItemDao;
    private static ConfirmationPageController confirmationPageController = null;

    private ConfirmationPageController(LineItemDao lineItemDao) {
        this.lineItemDao = lineItemDao;
    }

    public static ConfirmationPageController getInstance(LineItemDao lineItemDao) {
        if (confirmationPageController == null) {
            confirmationPageController = new ConfirmationPageController(lineItemDao);
        }
        return confirmationPageController;
    }

    @Override
    public String render(Request req, Response res) throws IOException {

        LineItemDao lineItemDao = this.lineItemDao;
        List<LineItem> order = lineItemDao.getAll();

        if (lineItemDao.getStatus() == OrderStatus.CHECKEDOUT) {
            lineItemDao.setStatus(OrderStatus.PAID);
        } else {
            res.redirect("/");
            return "";
        }

        Map<String, Object> params = new HashMap<>();
        Map<String, String> checkoutData = Checkout.getCheckoutMap();

        params.put("firstName", checkoutData.get("firstName"));
        params.put("lastName", checkoutData.get("lastName"));
        params.put("email", checkoutData.get("email"));
        params.put("phoneNumber", checkoutData.get("phoneNumber"));
        params.put("billingCountry", checkoutData.get("billingCountry"));
        params.put("billingCity", checkoutData.get("billingCity"));
        params.put("billingZipCode", checkoutData.get("billingZipCode"));
        params.put("billingAddress", checkoutData.get("billingAddress"));
        params.put("shippingCountry", checkoutData.get("shippingCountry"));
        params.put("shippingCity", checkoutData.get("shippingCity"));
        params.put("shippingZipCode", checkoutData.get("shippingZipCode"));
        params.put("shippingAddress", checkoutData.get("shippingAddress"));

        // Save log file
        lineItemDao.JSONFileWrite();
        params.put("cart", lineItemDao.getAll());
        params.put("totalPrice", lineItemDao.getTotalPrice());

        // Send email to user
        final String username = "aviation.codecoolers@gmail.com";
        final String password = "strongPass";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(checkoutData.get("email")));
            message.setSubject("Order receipt");
            StringBuilder orderText = new StringBuilder();
            for (LineItem item : order) {
                orderText.append(" - Product Name: ");
                orderText.append(item.getProduct().getName());
                orderText.append(" | Price: ");
                orderText.append(item.getProduct().getPrice());
                orderText.append(" | Quantity: ");
                orderText.append(item.getQuantity());
                orderText.append(" | Subtotal: ");
                orderText.append(item.getProduct().getDefaultPrice() * item.getQuantity());
                orderText.append(" ");
                orderText.append(item.getProduct().getDefaultCurrency());
                orderText.append("\n");
            }
            String messageText = "Dear " + checkoutData.get("firstName") +
                    ",\n\nthank you for your order.\n\n" +
                    "Order:\n" +
                    orderText +
                    "\nTotal: " +
                    lineItemDao.getTotalPrice() + " " +
                    lineItemDao.getAll().stream().findFirst().get().getProduct().getDefaultCurrency() +
                    "\n\nBilling Address:\n" +
                    checkoutData.get("firstName") + " " + checkoutData.get("lastName") + "\n" +
                    checkoutData.get("billingCountry") + "\n" +
                    checkoutData.get("billingCity") + "\n" +
                    checkoutData.get("billingZipCode") + "\n" +
                    checkoutData.get("billingAddress") + "\n" +
                    "\nShipping Address:\n" +
                    checkoutData.get("shippingCountry") + "\n" +
                    checkoutData.get("shippingCity") + "\n" +
                    checkoutData.get("shippingZipCode") + "\n" +
                    checkoutData.get("shippingAddress");
            message.setText(messageText);

            Transport.send(message);

            System.out.println("E-mail sent");

        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }

        lineItemDao.removeAll();
        lineItemDao.setStatus(OrderStatus.NEW);

        return renderTemplate(params, "confirmation");
    }
}
