package com.solvd.bankmanagement.bank.service;

import com.solvd.bankmanagement.bank.domain.Customer;

public interface CustomerService {
    Customer create(Customer customer, Long bankId);
    void update(Customer customer);
    Customer findById(Long id);
    boolean deleteById(Long id);
    boolean deleteAllByBankId(Long bankId);

}
