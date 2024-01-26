package com.solvd.bankmanagement.bank.domain;

import com.solvd.bankmanagement.bank.parser.JAXBAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.math.BigDecimal;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Account {
    @XmlAttribute(name = "id")
    private Long id;
    private String type;
    private String currency;
    private BigDecimal balance;
    private Long customerId;
//    @XmlJavaTypeAdapter(JAXBAdapter.class)
//    private LocalDate creationDate;

    public Account() {
    }

    public Account(String type, String currency, BigDecimal balance) {
        this.type = type;
        this.currency = currency;
        this.balance = balance;
    }

    public Account(Long id, String type, String currency, BigDecimal balance, Long customerId) {
        this.id = id;
        this.type = type;
        this.currency = currency;
        this.balance = balance;
        this.customerId = customerId;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public String getType() {
        return type;
    }
    public String getCurrency() {
        return currency;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    @Override
    public String toString() {
        return type + ", " + currency + ", " + customerId;
    }


}
