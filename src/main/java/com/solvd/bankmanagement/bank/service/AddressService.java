package com.solvd.bankmanagement.bank.service;

import com.solvd.bankmanagement.bank.domain.Address;
import com.solvd.bankmanagement.bank.domain.Bank;

public interface AddressService {
    Address create(Address address);
    Address findById(Long id);
}
