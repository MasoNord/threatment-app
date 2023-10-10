package org.project.app.db;

import org.project.app.config.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateConnection {
    private final Configuration config = new Configuration();
    private static Connection conn = null;
    public Connection connect() {
        try {
            conn = DriverManager.getConnection(config.getDb_url(), config.getDb_user(), config.getDb_password());
            System.out.println("Connected to the db");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
    public Connection getConnection(){
        return conn;
    }
}
