package com.codecool.shop.model;

import java.util.HashMap;
import java.util.Map;

public class Checkout {
    private String name,firstName,lastName, email, phoneNumber;
    private String billingCountry, billingCity, billingZipCode, billingAddress;
    private String shippingCountry, shippingCity, shippingZipCode, shippingAddress;

    public static Map<String, String> getCheckoutMap() {
        return checkoutMap;
    }

    private static Map<String, String> checkoutMap = new HashMap<>();
    private static Checkout checkout = null;

    private Checkout(Map checkoutData) {
        name = (String) checkoutData.get("name");
        firstName = (String) checkoutData.get("firstName");
        lastName = (String) checkoutData.get("lastName");
        email = (String) checkoutData.get("email");
        phoneNumber = (String) checkoutData.get("phoneNumber");
        billingCountry = (String) checkoutData.get("billingCountry");
        billingCity = (String) checkoutData.get("billingCity");
        billingZipCode = (String) checkoutData.get("billingZipCode");
        billingAddress = (String) checkoutData.get("billingAddress");
        shippingCountry = (String) checkoutData.get("shippingCountry");
        shippingCity = (String) checkoutData.get("shippingCity");
        shippingZipCode = (String) checkoutData.get("shippingZipCode");
        shippingAddress = (String) checkoutData.get("shippingAddress");
        checkoutMap.put("name", firstName + " " + lastName);
        checkoutMap.put("firstName", firstName);
        checkoutMap.put("lastName", lastName);
        checkoutMap.put("email", email);
        checkoutMap.put("phoneNumber", phoneFormat(phoneNumber));
        checkoutMap.put("billingCountry", billingCountry);
        checkoutMap.put("billingCity", billingCity);
        checkoutMap.put("billingZipCode", billingZipCode);
        checkoutMap.put("billingAddress", billingAddress);
        checkoutMap.put("shippingCountry", shippingCountry);
        checkoutMap.put("shippingCity", shippingCity);
        checkoutMap.put("shippingZipCode", shippingZipCode);
        checkoutMap.put("shippingAddress", shippingAddress);
    }

    private static String phoneFormat(String phoneNumber) {
        if (phoneNumber.length() == 11) {
            phoneNumber = String.format("%s-%s-%s-%s",
                    phoneNumber.substring(0, 2),
                    phoneNumber.substring(2, 4),
                    phoneNumber.substring(4, 7),
                    phoneNumber.substring(7, 11));
        }
        return phoneNumber;
    }

    public static Checkout getInstance(Map checkoutData) {
        if (checkout == null) {
            checkout = new Checkout(checkoutData);
        }
        return checkout;
    }


    public void setFirstName(String firstName) {
        checkoutMap.put("firsName", firstName);
    }

    public void setLastName(String lastName) {
        checkoutMap.put("lastName", lastName);
    }

    public void setEmail(String email) {
        checkoutMap.put("email", email);
    }

    public void setPhoneNumber(String phoneNumber) {
        checkoutMap.put("phoneNumber", phoneFormat(phoneNumber));

    }

    public void setBillingCountry(String billingCountry) {
        checkoutMap.put("billingCountry", billingCountry);

    }

    public void setBillingCity(String billingCity) {
        checkoutMap.put("billingCity", billingCity);

    }

    public void setBillingZipCode(String billingZipCode) {
        checkoutMap.put("billingZipCode", billingZipCode);

    }

    public void setBillingAddress(String billingAddress) {
        checkoutMap.put("billingAddress", billingAddress);
    }

    public void setShippingCountry(String shippingCountry) {

        checkoutMap.put("shippingCountry", shippingCountry);
    }

    public void setShippingCity(String shippingCity) {
        checkoutMap.put("shippingCity", shippingCity);
    }

    public void setShippingZipCode(String shippingZipCode) {
        checkoutMap.put("shippingZipCode", shippingZipCode);

    }

    public void setShippingAddress(String shippingAddress) {
        checkoutMap.put("shippingAddress", shippingAddress);

    }

    public static Checkout getCheckout() {
        return checkout;
    }
}