package com.sms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:db/student_management.db";
    private static Connection connection = null;

    private DBConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load driver explicitly (sometimes needed for shade/fat jars, good practice)
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(URL);
                System.out.println("Connected to database successfully.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database Connection Failed: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
    
    // Optional: method to initialize tables if they don't exist (can call schema.sql content)
    // For now, we assume schema.sql is run externally or we can implement a runner later.
}
