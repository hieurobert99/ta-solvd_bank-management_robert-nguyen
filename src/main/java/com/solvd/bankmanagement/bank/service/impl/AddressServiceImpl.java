package com.solvd.bankmanagement.bank.service.impl;

import com.solvd.bankmanagement.bank.domain.Address;
import com.solvd.bankmanagement.bank.persistence.AddressRepository;
import com.solvd.bankmanagement.bank.service.AddressService;

public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(Address address) {
        address.setId(null);
        addressRepository.create(address);
        return address;
    }

    @Override
    public Address findById(Long id) {
        Address address = addressRepository.findById(id);
        if(address == null) {
            System.out.println("Address of ID: " + id + " not found");
        } else {
            System.out.println("Address found: " + address);
        }
        return address;
    }
}
