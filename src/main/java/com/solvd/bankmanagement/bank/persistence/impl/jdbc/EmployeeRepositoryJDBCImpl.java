package com.solvd.bankmanagement.bank.persistence.impl.jdbc;

import com.solvd.bankmanagement.bank.domain.Employee;
import com.solvd.bankmanagement.bank.persistence.EmployeeRepository;
import com.solvd.bankmanagement.bank.persistence.connections.ConnectionConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryJDBCImpl implements EmployeeRepository {
    private static final String INSERT_NEW_EMPLOYEE = "INSERT INTO bank.Employees (first_name, last_name, address_id, bank_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_EMPLOYEE = "UPDATE bank.Employees SET first_name = ?, last_name = ?, address_id = ?, bank_id = ? WHERE id = ?";
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM bank.Employees WHERE id = ?";
    private static final String DELETE_EMPLOYEE_BY_ID = "DELETE FROM bank.Employees WHERE id = ?";
    private static final String SELECT_EMPLOYEES_BY_BANK_ID = "SELECT * FROM bank.Employees WHERE bank_id = ?";

    @Override
    public void create(Employee employee, Long bankId) {
        Connection connection = ConnectionConfig.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(INSERT_NEW_EMPLOYEE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setLong(3, employee.getAddressId());
            employee.setBankId(bankId);
            statement.setLong(4, employee.getBankId());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                employee.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public void update(Employee employee) {
        Connection connection = ConnectionConfig.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE)) {

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setLong(3, employee.getAddressId());
            statement.setLong(4, employee.getBankId());
            statement.setLong(5, employee.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Failed to update employee");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public Employee findById(Long id) {
        Connection connection = ConnectionConfig.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                return resultSet.next() ? mapResultSetToEmployee(resultSet) : null;
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
        try (PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE_BY_ID)){
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Employee by ID:" + id + " not found.");
        }  finally {
            ConnectionConfig.releaseConnection(connection);
        }
    }

    @Override
    public List<Employee> findAllByBankId(Long bankId) {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = ConnectionConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_EMPLOYEES_BY_BANK_ID)) {

            statement.setLong(1, bankId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Employee employee = mapResultSetToEmployee(resultSet);
                    employees.add(employee);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving employees by bank ID", e);
        }

        return employees;
    }

    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        return new Employee(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getLong("address_id"),
                resultSet.getLong("bank_id")
        );
    }
}
