package com.solvd.bankmanagement.bank.persistence.impl.jdbc;

import com.solvd.bankmanagement.bank.domain.Address;

import com.solvd.bankmanagement.bank.persistence.AddressRepository;
import com.solvd.bankmanagement.bank.persistence.connections.ConnectionConfig;

import java.sql.*;

public class AddressRepositoryJDBCImpl implements AddressRepository {
    private static final String INSERT_NEW_ADDRESS = "INSERT INTO bank.Addresses (street, city) VALUES (?, ?)";
    private static final String SELECT_ADDRESS_BY_ID = "SELECT * FROM bank.Addresses WHERE id = ?";

    @Override
    public void create(Address address) {
        Connection connection = ConnectionConfig.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_ADDRESS, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, address.getStreet());
            statement.setString(2, address.getCity());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                address.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public Address findById(Long id) {
        Connection connection = ConnectionConfig.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ADDRESS_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                return resultSet.next() ? mapResultSetToAddress(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Address by ID: " + id + " not found.");
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    private Address mapResultSetToAddress(ResultSet resultSet) throws SQLException {
        Address address = new Address(
                resultSet.getLong("id"),
                resultSet.getString("street"),
                resultSet.getString("city")
        );
        return address;
    }
}
