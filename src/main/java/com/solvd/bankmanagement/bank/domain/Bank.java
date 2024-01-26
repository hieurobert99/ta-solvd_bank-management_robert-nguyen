package com.solvd.bankmanagement.bank.domain;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "bank")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bank {
    @XmlAttribute(name = "id")
    private Long id;
    private String name;
    private Long addressId;

    @XmlElementWrapper(name = "employees")
    @XmlElement(name = "employee")
    private List<Employee> employees;
    @XmlElementWrapper(name = "customers")
    @XmlElement(name = "customer")
    private List<Customer> customers;

    public Bank() {
    }

    public Bank(String name, Long addressId) {
        this.name = name;
        this.addressId = addressId;
    }

    public Bank(Long id, String name, Long addressId) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    public List<Customer> getCustomers() {
        return customers;
    }
    public String getName() {
        return name;
    }
    public Long getAddressId() {
        return addressId;
    }
    @Override
    public String toString() {
        return name + ", " + addressId.toString();
    }
}
