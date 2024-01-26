package com.solvd.bankmanagement.bank.persistence.impl.jdbc;

import com.solvd.bankmanagement.bank.domain.Account;
import com.solvd.bankmanagement.bank.persistence.AccountRepository;
import com.solvd.bankmanagement.bank.persistence.connections.ConnectionConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryJDBCImpl implements AccountRepository {
    private static final String INSERT_NEW_ACCOUNT = "INSERT INTO bank.Accounts (type, currency, balance, customer_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ACCOUNT = "UPDATE bank.Accounts SET type = ?, currency = ?, balance = ?, customer_id = ? WHERE id = ?";
    private static final String SELECT_ACCOUNT_BY_ID = "SELECT * FROM bank.Accounts WHERE id = ?";
    private static final String DELETE_ACCOUNT_BY_ID = "DELETE FROM bank.Accounts WHERE id = ?";
    private static final String SELECT_ACCOUNTS_BY_CUSTOMER_ID = "SELECT * FROM bank.Accounts WHERE customer_id = ?";


    @Override
    public void create(Account account, Long customerId) {
        Connection connection = ConnectionConfig.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, account.getType());
            statement.setString(2, account.getCurrency());
            statement.setBigDecimal(3, account.getBalance());
            account.setCustomerId(customerId);
            statement.setLong(4, account.getCustomerId());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                account.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }


    @Override
    public void update(Account account) {

        Connection connection = ConnectionConfig.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT)) {

            statement.setString(1, account.getType());
            statement.setString(2, account.getCurrency());
            statement.setBigDecimal(3, account.getBalance());
            statement.setLong(4, account.getCustomerId());
            statement.setLong(5, account.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Failed to update account");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public Account findById(Long id) {
        Connection connection = ConnectionConfig.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                return resultSet.next() ? mapResultSetToAccount(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Employee by ID: " + id + " not found.");
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Connection connection = ConnectionConfig.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ACCOUNT_BY_ID)){
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Account by ID:" + id + " not found.");
        }  finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public List<Account> findAllByCustomerId(Long customerId) {
        List<Account> accounts = new ArrayList<>();

        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNTS_BY_CUSTOMER_ID)) {

            statement.setLong(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = mapResultSetToAccount(resultSet);
                    accounts.add(account);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving accounts by customer ID", e);
        }

        return accounts;
    }

    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        return new Account(
                resultSet.getLong("id"),
                resultSet.getString("type"),
                resultSet.getString("currency"),
                resultSet.getBigDecimal("balance"),
                resultSet.getLong("customer_id")
        );
    }
}
