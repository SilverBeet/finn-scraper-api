package com.finn.finnapi.models;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class DBController {
    private static Connection connection;
    private static Dotenv dotenv = Dotenv.load();

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dotenv.get("CONNECTION_STRING"), dotenv.get("DB_USER"),
                    dotenv.get("DB_PASS"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
