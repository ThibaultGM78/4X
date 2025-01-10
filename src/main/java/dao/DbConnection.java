/**
 * @file DbConnection.java
 * @brief Utility class for managing database connections.
 *
 * This class provides methods to establish a connection to the database and to test the connection.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/4x";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * @brief Establishes a connection to the database.
     * @return A Connection object representing the database connection.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connected to DB");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * @brief Tests the database connection.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the JDBC driver class is not found.
     */
    public static void test() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connection created");
    }
}
