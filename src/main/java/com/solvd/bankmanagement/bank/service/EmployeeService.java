package com.solvd.bankmanagement.bank.service;

import com.solvd.bankmanagement.bank.domain.Employee;

public interface EmployeeService {
    Employee create(Employee employee, Long bankId);
    void update(Employee employee);
    Employee findById(Long id);
    boolean deleteById(Long id);
    boolean deleteAllByBankId(Long bankId);
}
