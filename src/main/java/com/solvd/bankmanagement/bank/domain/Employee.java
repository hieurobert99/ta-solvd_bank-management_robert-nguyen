package com.solvd.bankmanagement.bank.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.solvd.bankmanagement.bank.parser.JacksonAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {
    @XmlAttribute(name = "id")
    private Long id;
    private String firstName;
    private String lastName;
//    @JsonDeserialize(using = JacksonAdapter.class)
//    private LocalDate dateOfJoin;
    private Long addressId;
    private Long bankId;


    public Employee() {
    }
    public Employee(Long id, String firstName, String lastName, Long addressId, Long bankId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
        this.bankId = bankId;
    }

    public Employee(String firstName, String lastName, Long addressId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBankId() {
        return bankId;
    }
    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return firstName + ", " + lastName + ", id: " + id;
    }

}
