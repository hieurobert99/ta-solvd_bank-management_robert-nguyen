package com.solvd.bankmanagement.bank.service.impl;

import com.solvd.bankmanagement.bank.domain.Employee;
import com.solvd.bankmanagement.bank.persistence.EmployeeRepository;
import com.solvd.bankmanagement.bank.service.EmployeeService;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Employee create(Employee employee, Long bankId) {
        employee.setId(null);
        employeeRepository.create(employee, bankId);
        return employee;
    }

    @Override
    public void update(Employee employee) {
        employeeRepository.update(employee);
    }

    @Override
    public Employee findById(Long id) {
        Employee employee = employeeRepository.findById(id);
        if(employee == null) {
            System.out.println("Employee of ID: " + id + " not found");
        } else {
            System.out.println("Employee found: " + employee);
        }
        return employee;
    }

    @Override
    public boolean deleteById(Long id) {
        if(findById(id) == null){
            return false;
        } else {
            return employeeRepository.deleteById(id);
        }
    }

    @Override
    public boolean deleteAllByBankId(Long bankId) {
        List<Employee> employeesToDelete = employeeRepository.findAllByBankId(bankId);

        if (!employeesToDelete.isEmpty()) {
            for (Employee employee : employeesToDelete) {
                employeeRepository.deleteById(employee.getId());
            }
            return true;
        } else {
            System.out.println("No accounts found for customer ID: " + bankId);
            return false;
        }
    }
}
