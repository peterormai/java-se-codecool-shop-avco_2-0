package com.codecool.shop.model;

public class Cart {
    private static Cart ourInstance = new Cart();

    public static Cart getInstance() {
        return ourInstance;
    }

    private Cart() {
    }
}
