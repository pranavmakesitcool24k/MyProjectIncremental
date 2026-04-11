package com.edutech.progressive.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnectionManager {

    private static Properties properties = new Properties();

    private static void loadProperties() {
        try (InputStream input =
                 DatabaseConnectionManager.class
                     .getClassLoader()
                     .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("application.properties file not found");
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Error reading application.properties file", e);
        }
    }

    public static Connection getConnection() {
        try {
            if (properties.isEmpty()) {
                loadProperties();
            }

            String driver = properties.getProperty("db.driver");
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create database connection", e);
        }
    }
}