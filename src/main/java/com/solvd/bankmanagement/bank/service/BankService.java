package com.solvd.bankmanagement.bank.service;

import com.solvd.bankmanagement.bank.domain.Bank;
import com.solvd.bankmanagement.bank.domain.Customer;
import com.solvd.bankmanagement.bank.domain.Employee;


import java.util.List;

public interface BankService {
    Bank create(Bank bank);
    void update(Bank bank);
    Bank findById(Long id);
    boolean deleteById(Long id);

}
