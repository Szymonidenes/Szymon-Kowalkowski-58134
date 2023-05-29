package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    private final static String url = "jdbc:mysql://localhost:3306/employeedb?useSSL=false";
    private final static String username = "sa";
    private final static String password = "1234";
    private static Connection conn;

    public static Connection createDbConnection() {

        try {
            // ładowanie sterownika
            Class.forName("com.mysql.cj.jdbc.Driver");

            // połączenie do bazy
            conn = DriverManager.getConnection(url, username, password);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return conn;
    }
}
