package com.codecool.shop.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {
    private String filePath;
    private static Connection connection;

    public ConnectionManager(String filePath) {
        this.filePath = filePath;
        try {
            connection = createConnectionConfig();

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private Connection createConnectionConfig() throws SQLException {
        String DB_URL = readIndexOfLines(1).replace("\n","");
        String DB_NAME = readIndexOfLines(2);
        String DB_USER = readIndexOfLines(3);
        String DB_PASSWORD = readIndexOfLines(4);
        return DriverManager.getConnection(
                DB_URL + DB_NAME,
                DB_USER,
                DB_PASSWORD);
    }

    private String readIndexOfLines(int index) {
        List<String> outputList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while (reader.ready()) {
                outputList.add(reader.readLine());
            }
            reader.close();
        } catch (IOException io) {
            throw new IllegalArgumentException(io);

        }
        return outputList.get(index - 1);
    }
}
