package com.solvd.bankmanagement.bank.service.impl;

import com.solvd.bankmanagement.bank.domain.Bank;
import com.solvd.bankmanagement.bank.domain.Customer;
import com.solvd.bankmanagement.bank.domain.Employee;
import com.solvd.bankmanagement.bank.persistence.BankRepository;
import com.solvd.bankmanagement.bank.service.BankService;
import com.solvd.bankmanagement.bank.service.CustomerService;
import com.solvd.bankmanagement.bank.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final EmployeeService employeeService;
    private final CustomerService customerService;

    public BankServiceImpl(BankRepository bankRepository, EmployeeService employeeService, CustomerService customerService) {
        this.bankRepository = bankRepository;
        this.employeeService = employeeService;
        this.customerService = customerService;
    }

    @Override
    public Bank create(Bank bank) {
        bank.setId(null);
        bankRepository.create(bank);

        if(bank.getEmployees() != null) {
            List<Employee> employees = bank.getEmployees().stream()
                    .map(employee -> employeeService.create(employee, bank.getId()))
                    .collect(Collectors.toList());
            bank.setEmployees(employees);
        }

        if(bank.getCustomers() != null) {
            List<Customer> customers = bank.getCustomers().stream()
                    .map(customer -> customerService.create(customer, bank.getId()))
                    .collect(Collectors.toList());
            bank.setCustomers(customers);
        }

        return bank;
    }

    @Override
    public void update(Bank bank) {
        bankRepository.update(bank);
    }

    @Override
    public Bank findById(Long id) {
        Bank bank = bankRepository.findById(id);
        if(bank == null) {
            System.out.println("Bank of ID: " + id + " not found");
        } else {
            System.out.println("Bank found: " + bank);
        }
        return bank;
    }

    @Override
    public boolean deleteById(Long id) {
        if(findById(id) == null){
            return false;
        } else {
            boolean employeesDeleted = employeeService.deleteAllByBankId(id);
            boolean customersDeleted = customerService.deleteAllByBankId(id);

            if (employeesDeleted && customersDeleted){
                return bankRepository.deleteById(id);
            } else {
                System.out.println("Unable to delete bank. Associated employees or customers still exist.");
                return false;
            }
        }
    }
}