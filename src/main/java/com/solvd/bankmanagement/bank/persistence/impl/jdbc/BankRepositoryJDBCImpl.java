package com.solvd.bankmanagement.bank.persistence.impl.jdbc;

import com.solvd.bankmanagement.bank.domain.Bank;
import com.solvd.bankmanagement.bank.persistence.BankRepository;
import com.solvd.bankmanagement.bank.persistence.connections.ConnectionConfig;

import java.sql.*;

public class BankRepositoryJDBCImpl implements BankRepository {
    private static final String INSERT_NEW_BANK = "INSERT INTO bank.Bank (name, address_id) VALUES (?, ?)";
    private static final String UPDATE_BANK = "UPDATE bank.Bank SET name = ?, address_id = ? WHERE id = ?";
    private static final String SELECT_BANK_BY_ID = "SELECT * FROM bank.Bank WHERE id = ?";
    private static final String DELETE_BANK_BY_ID = "DELETE FROM bank.Bank WHERE id = ?";

    @Override
    public void create(Bank bank) {
        Connection connection = ConnectionConfig.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_BANK, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, bank.getName());
            statement.setLong(2, bank.getAddressId());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                bank.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public void update(Bank bank) {
        Connection connection = ConnectionConfig.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BANK)) {

            statement.setString(1, bank.getName());
            statement.setLong(2, bank.getAddressId());
            statement.setLong(3, bank.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Failed to update bank");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }


    @Override
    public Bank findById(Long id) {
        Connection connection = ConnectionConfig.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_BANK_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                return resultSet.next() ? mapResultSetToBank(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Bank by ID: " + id + " not found.");
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Connection connection = ConnectionConfig.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BANK_BY_ID)){
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Bank by ID:" + id + " not found.");
        }  finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    private Bank mapResultSetToBank(ResultSet resultSet) throws SQLException {
        Bank bank = new Bank(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getLong("address_id")
        );

        return bank;
    }

}
