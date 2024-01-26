package com.solvd.bankmanagement.bank.service.impl;

import com.solvd.bankmanagement.bank.domain.Account;

import com.solvd.bankmanagement.bank.persistence.AccountRepository;
import com.solvd.bankmanagement.bank.service.AccountService;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account create(Account account, Long customerId) {
        account.setId(null);
        accountRepository.create(account, customerId);
        return account;
    }

    @Override
    public void update(Account account) {
        accountRepository.update(account);
    }

    @Override
    public Account findById(Long id) {
        Account account = accountRepository.findById(id);
        if(account == null) {
            System.out.println("Account of ID: " + id + " not found");
        } else {
            System.out.println("Account found: " + account);
        }
        return account;
    }

    @Override
    public boolean deleteById(Long id) {
        if(findById(id) == null){
            return false;
        } else {
            return accountRepository.deleteById(id);
        }
    }

    @Override
    public boolean deleteAllByCustomerId(Long customerId) {
        List<Account> accountsToDelete = accountRepository.findAllByCustomerId(customerId);
        if (!accountsToDelete.isEmpty()) {
            for (Account account : accountsToDelete) {
                accountRepository.deleteById(account.getId());
            }
            return true;
        } else {
            System.out.println("No accounts found for customer ID: " + customerId);
            return false;
        }
    }

}
