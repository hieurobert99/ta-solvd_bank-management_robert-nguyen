package com.solvd.bankmanagement.bank.persistence.connections;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionConfig {

    //singleton
    private ConnectionConfig() {
        throw new UnsupportedOperationException("ConnectionConfig class cannot be instantiated.");
    }

    public static Connection getConnection() {
        try {
            return ConnectionPool.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create connection.", e);
        }

    }

    public static void releaseConnection(Connection connection) {
        ConnectionPool.getInstance().releaseConnection(connection);
    }
}
