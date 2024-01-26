package com.solvd.bankmanagement.bank.persistence;

import com.solvd.bankmanagement.bank.domain.Address;

public interface AddressRepository {
    void create(Address address);
    Address findById(Long id);
}
