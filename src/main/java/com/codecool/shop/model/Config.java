package com.codecool.shop.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Config {
    private static Config ourInstance;
    private static Connection connection;

    public static Config getInstance() {
        if (ourInstance == null) {
            ourInstance = new Config();
        }
        return ourInstance;
    }

    private Config() {
        try{
            connection = getConnection();

        } catch (Exception e){

        }

    }

    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                "",
                "",
                "");
    }


}
