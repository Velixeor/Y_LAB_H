package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataBaseConnection {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/db";
    private static final String USER = "admin";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}
