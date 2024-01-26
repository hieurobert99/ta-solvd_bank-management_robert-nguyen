package com.solvd.bankmanagement.bank.service;

import com.solvd.bankmanagement.bank.domain.Account;

public interface AccountService {
    Account create(Account account, Long customerId);
    void update(Account account);
    Account findById(Long id);
    boolean deleteById(Long id);
    boolean deleteAllByCustomerId(Long customerId);
}
