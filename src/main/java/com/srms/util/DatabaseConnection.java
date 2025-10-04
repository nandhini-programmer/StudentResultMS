package com.srms.util;
// 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// java MySQL Connector dependency is required in your project to use this class
public class DatabaseConnection {
    
    // CHANGE THESE IF YOUR MYSQL PASSWORD IS DIFFERENT
    private static final String URL = "jdbc:mysql://localhost:3306/student_result_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "learning";
    
    private static Connection connection = null;
    
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✅ Database Connected Successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Connection Failed! Check MySQL is running");
            e.printStackTrace();
        }
        return connection;
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database Connection Closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}