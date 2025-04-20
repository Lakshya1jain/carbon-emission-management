package com.carbon.emissions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for handling JDBC connections to the carbon_emissions_db database
 */
public class CarbonEmissionsDBConnection {
    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/carbon_emissions_db";
    private static final String USER = "root";
    private static final String PASSWORD = "lakshya@123"; // Change this to your actual password
    
    /**
     * Gets a connection to the carbon_emissions_db database
     * @return a Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish the connection
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            
            if (connection != null) {
                System.out.println("Successfully connected to the carbon_emissions_db database!");
            }
            
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
    
    /**
     * Main method for testing the connection
     */
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Connection established successfully!");
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 