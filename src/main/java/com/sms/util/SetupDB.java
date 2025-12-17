package com.sms.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class SetupDB {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement()) {

            String sql = new String(Files.readAllBytes(Paths.get("db/schema.sql")));
            // Split by semicolon for multiple statements support if driver doesn't support
            // execBatch/bulk properly in one go
            // SQLite JDBC usually handles one statement per executeUpdate unless
            // encapsulated.
            // But simple split works for this simple schema.
            String[] statements = sql.split(";");

            conn.setAutoCommit(false);
            for (String s : statements) {
                if (!s.trim().isEmpty()) {
                    stmt.execute(s);
                }
            }
            conn.commit();
            System.out.println("Database Initialized Successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
