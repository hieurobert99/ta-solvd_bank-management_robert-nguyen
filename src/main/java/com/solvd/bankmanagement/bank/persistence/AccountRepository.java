package com.solvd.bankmanagement.bank.persistence;

import com.solvd.bankmanagement.bank.domain.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountRepository {
    void create(@Param("account") Account account, @Param("customerId") Long customerId);
    void update(Account account);
    Account findById(Long id);
    boolean deleteById(Long id);
    List<Account> findAllByCustomerId(Long customerId);
}


