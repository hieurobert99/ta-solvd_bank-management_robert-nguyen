package com.solvd.bankmanagement.bank.persistence;

import com.solvd.bankmanagement.bank.domain.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerRepository {
    void create(@Param("customer") Customer customer, @Param("bankId") Long bankId);
    void update(Customer customer);
    Customer findById(Long id);
    boolean deleteById(Long id);
    List<Customer> findAllByBankId(Long bankId);


}
