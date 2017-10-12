package com.codecool.shop.dao;

public class Switch {

    private static Switch instance = null;
    private static Enum dataHandling = DataHandler.MEMORY;

    /* A private Constructor prevents any other class from instantiating.
     */
    private Switch() {
    }

    public static Switch getInstance() {
        if (instance == null) {
            instance = new Switch();
        }
        return instance;
    }

    public Enum getDataHandling() {
        return dataHandling;
    }

}
