package com.solvd.bankmanagement.bank.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.solvd.bankmanagement.bank.parser.JacksonAdapter;

public class Address {
    @JsonDeserialize(using = JacksonAdapter.class)
    private Long id;
    private String street;
    private String city;

    public Address() {
    }
    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public Address(Long id, String street, String city) {
        this.id = id;
        this.street = street;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
