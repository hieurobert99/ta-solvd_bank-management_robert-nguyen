package com.solvd.bankmanagement.bank.persistence.impl.jdbc;

import com.solvd.bankmanagement.bank.domain.Customer;
import com.solvd.bankmanagement.bank.persistence.CustomerRepository;
import com.solvd.bankmanagement.bank.persistence.connections.ConnectionConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryJDBCImpl implements CustomerRepository {
    private static final String INSERT_NEW_CUSTOMER = "INSERT INTO bank.Customers (first_name, last_name, address_id, bank_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CUSTOMER = "UPDATE bank.Customers SET first_name = ?, last_name = ?, address_id = ?, bank_id = ? WHERE id = ?";
    private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM bank.Customers WHERE id = ?";
    private static final String DELETE_CUSTOMER_BY_ID = "DELETE FROM bank.Customers WHERE id = ?";
    private static final String SELECT_CUSTOMERS_BY_BANK_ID = "SELECT * FROM bank.Customers WHERE bank_id = ?";

    @Override
    public void create(Customer customer, Long bankId) {
        Connection connection = ConnectionConfig.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(INSERT_NEW_CUSTOMER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setLong(3, customer.getAddressId());
            customer.setBankId(bankId);
            statement.setLong(4, customer.getBankId());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                customer.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public void update(Customer customer) {
        Connection connection = ConnectionConfig.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER)) {

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setLong(3, customer.getAddressId());
            statement.setLong(4, customer.getBankId());
            statement.setLong(5, customer.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Failed to update customer");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public Customer findById(Long id) {
        Connection connection = ConnectionConfig.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()){
                return resultSet.next() ? mapResultSetToCustomer(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Customer by ID: " + id + " not found.");
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Connection connection = ConnectionConfig.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER_BY_ID)){
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Customer by ID:" + id + " not found.");
        }  finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public List<Customer> findAllByBankId(Long bankId) {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CUSTOMERS_BY_BANK_ID)) {

            statement.setLong(1, bankId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Customer customer = mapResultSetToCustomer(resultSet);
                    customers.add(customer);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving customers by bank ID", e);
        }

        return customers;
    }

    private Customer mapResultSetToCustomer(ResultSet resultSet) throws SQLException {
        return new Customer(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getLong("address_id"),
                resultSet.getLong("bank_id")
        );
    }

}
