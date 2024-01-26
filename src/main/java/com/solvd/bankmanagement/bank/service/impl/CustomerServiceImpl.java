package com.solvd.bankmanagement.bank.service.impl;

import com.solvd.bankmanagement.bank.domain.Account;
import com.solvd.bankmanagement.bank.domain.Customer;
import com.solvd.bankmanagement.bank.persistence.CustomerRepository;
import com.solvd.bankmanagement.bank.service.AccountService;
import com.solvd.bankmanagement.bank.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountService accountService;

    public CustomerServiceImpl(CustomerRepository customerRepository, AccountService accountService) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
    }


    @Override
    public Customer create(Customer customer, Long bankId) {
        customer.setId(null);
        customerRepository.create(customer, bankId);

        if(customer.getAccounts() != null) {
            List<Account> accounts = customer.getAccounts().stream()
                    .map(account -> accountService.create(account, customer.getId()))
                    .collect(Collectors.toList());
            customer.setAccounts(accounts);
        }
        return customer;
    }

    @Override
    public void update(Customer customer) {
        customerRepository.update(customer);
    }

    @Override
    public Customer findById(Long id) {
        Customer customer = customerRepository.findById(id);
        if(customer == null) {
            System.out.println("Customer of ID: " + id + " not found");
        } else {
            System.out.println("Customer found: " + customer);
        }
        return customer;
    }

    @Override
    public boolean deleteById(Long id) {
        if(findById(id) == null){
            return false;
        } else {
            boolean accountsDeleted = accountService.deleteAllByCustomerId(id);
            if (accountsDeleted){
                return customerRepository.deleteById(id);
            } else {
                System.out.println("Unable to delete customer. Associated accounts still exist.");
                return false;
            }
        }
    }

    public boolean deleteAllByBankId(Long bankId) {
        List<Customer> customersToDelete = customerRepository.findAllByBankId(bankId);

        if (!customersToDelete.isEmpty()) {
            for (Customer customer : customersToDelete) {
                customerRepository.deleteById(customer.getId());
            }
            return true;
        } else {
            System.out.println("No accounts found for customer ID: " + bankId);
            return false;
        }
    }
}
