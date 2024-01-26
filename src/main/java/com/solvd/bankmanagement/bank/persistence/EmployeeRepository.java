package com.solvd.bankmanagement.bank.persistence;

import com.solvd.bankmanagement.bank.domain.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeRepository {
    void create(@Param("employee") Employee employee, @Param("bankId") Long bankId);
    void update(Employee employee);
    Employee findById(Long id);
    boolean deleteById(Long id);
    List<Employee> findAllByBankId(Long bankId);

}
