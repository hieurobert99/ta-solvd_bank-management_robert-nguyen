package com.solvd.bankmanagement.bank.persistence;

import com.solvd.bankmanagement.bank.domain.Bank;

public interface BankRepository {
    void create(Bank bank);
    void update(Bank bank);
    Bank findById(Long id);
    boolean deleteById(Long id);
//    void addEmployee(Long id, Long employeeId);

}
