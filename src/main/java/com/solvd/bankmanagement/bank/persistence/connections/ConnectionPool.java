package com.solvd.bankmanagement.bank.persistence.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static final String URL = "jdbc:mysql://localhost:3306/?user=root";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private List<Connection> connections;
    private List<Connection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;

    private ConnectionPool() {
        this.connections = new ArrayList<>(INITIAL_POOL_SIZE);
        initializeConnectionPool();
    }

    private void initializeConnectionPool() {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connections.add(createConnection());
        }
    }

    private static Connection createConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected.");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create connection.", e);
        }
    }

    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.INSTANCE;
    }

    private static class ConnectionPoolHolder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public Connection getConnection() throws SQLException {
        if (connections.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connections.add(createConnection());
            } else {
                throw new RuntimeException("Maxed-out pool size.");
            }
        }
        Connection connection = connections.remove(connections.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public void releaseConnection(Connection connection) {
        usedConnections.remove(connection);
        connections.add(connection);
    }
}
