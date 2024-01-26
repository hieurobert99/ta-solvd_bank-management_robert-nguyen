package com.solvd.bankmanagement.bank.parser;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JAXBAdapter extends XmlAdapter<String, LocalDate> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public LocalDate unmarshal(String s){
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(s, DATE_FORMATTER);
    }

    @Override
    public String marshal(LocalDate localDate){
        if (localDate == null) {
            return null;
        }
        return localDate.format(DATE_FORMATTER);
    }
}
