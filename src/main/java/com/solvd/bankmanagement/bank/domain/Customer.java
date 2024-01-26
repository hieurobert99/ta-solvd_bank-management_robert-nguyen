package com.solvd.bankmanagement.bank.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.solvd.bankmanagement.bank.parser.JacksonAdapter;
import jakarta.xml.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
    @XmlAttribute(name = "id")
    private Long id;
    private String firstName;
    private String lastName;
//    @JsonDeserialize(using = JacksonAdapter.class)
//    private LocalDate dateOfJoin;
    private Long addressId;
    private Long bankId;
    @XmlElementWrapper(name = "accounts")
    @XmlElement(name = "account")
    private List<Account> accounts;


    public Customer() {
    }
    public Customer(Long id, String firstName, String lastName, Long addressId, Long bankId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
        this.bankId = bankId;
    }

    public Customer(String firstName, String lastName, Long addressId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getBankId() {
        return bankId;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<Account> getAccounts() {
        return accounts;
    }
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return firstName + ", " + lastName + ", id: " + id;
    }

}
