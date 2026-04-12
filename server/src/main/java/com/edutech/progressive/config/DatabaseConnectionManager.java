package com.edutech.progressive.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnectionManager {

    private static final Properties properties = new Properties();
    private static volatile boolean schemaInitialized = false;

    private static void loadProperties() {
        if (!properties.isEmpty()) return;
        try (InputStream input = DatabaseConnectionManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load properties", e);
        }
    }

    private static String resolve(String value) {
        if (value == null) return null;
        String v = value.trim();
        if (v.startsWith("${") && v.endsWith("}") && v.length() > 3) {
            String key = v.substring(2, v.length() - 1);
            String sys = System.getProperty(key);
            if (sys != null && !sys.isEmpty()) return sys;
            String env = System.getenv(key);
            if (env != null && !env.isEmpty()) return env;
            return null;
        }
        return v;
    }

    private static String pick(String... keys) {
        for (String key : keys) {
            String sys = System.getProperty(key);
            if (sys != null && !sys.trim().isEmpty()) return sys.trim();
            String env = System.getenv(key);
            if (env != null && !env.trim().isEmpty()) return env.trim();
            String prop = resolve(properties.getProperty(key));
            if (prop != null && !prop.trim().isEmpty()) return prop.trim();
        }
        return null;
    }

    private static void initSchema(Connection con) {
        if (schemaInitialized) return;
        synchronized (DatabaseConnectionManager.class) {
            if (schemaInitialized) return;
            try (Statement st = con.createStatement()) {
                st.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS team (" +
                                "team_id INT PRIMARY KEY AUTO_INCREMENT," +
                                "team_name VARCHAR(100) NOT NULL," +
                                "location VARCHAR(100)," +
                                "owner_name VARCHAR(100)," +
                                "establishment_year INT" +
                                ")"
                );

                st.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS cricketer (" +
                                "cricketer_id INT PRIMARY KEY AUTO_INCREMENT," +
                                "team_id INT," +
                                "cricketer_name VARCHAR(100) NOT NULL," +
                                "age INT," +
                                "nationality VARCHAR(100)," +
                                "experience INT," +
                                "role VARCHAR(50)," +
                                "total_runs INT," +
                                "total_wickets INT" +
                                ")"
                );

                st.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS matches (" +
                                "match_id INT PRIMARY KEY AUTO_INCREMENT," +
                                "first_team_id INT NOT NULL," +
                                "second_team_id INT NOT NULL," +
                                "match_date DATE NOT NULL," +
                                "venue VARCHAR(100)," +
                                "result VARCHAR(100)," +
                                "status VARCHAR(100)," +
                                "winner_team_id INT" +
                                ")"
                );

                schemaInitialized = true;
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize schema", e);
            }
        }
    }

    public static Connection getConnection() {
        try {
            loadProperties();

            String url = pick("MYSQL_CONNECTION_URL", "spring.datasource.url", "db.url");
            String username = pick("MYSQL_CONNECTION_USER", "spring.datasource.username", "db.username");
            String password = pick("MYSQL_CONNECTION_PASSWORD", "spring.datasource.password", "db.password");
            String driver = pick("MYSQL_CONNECTION_DRIVER", "spring.datasource.driver-class-name", "db.driver");

            if (driver != null && !driver.isEmpty()) Class.forName(driver);

            Connection con = DriverManager.getConnection(url, username, password);
            initSchema(con);
            return con;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create connection", e);
        }
    }
}