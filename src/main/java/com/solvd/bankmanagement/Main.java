package com.solvd.bankmanagement;

import com.solvd.bankmanagement.bank.parser.Parser;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        File file = new File(
                "C:\\Users\\Papa Robert\\solvd_ta\\dao-bank-robert_nguyen\\src\\main\\resources\\bank.xml"
        );
        File jsonFile = new File(
                "C:\\Users\\Papa Robert\\solvd_ta\\dao-bank-robert_nguyen\\src\\main\\resources\\bank.json"
        );

        //SAX
        System.out.println("------------------ SAX PARSER ------------------");
        Parser.parseXMLWithSAX(file);

        //JAXB
        System.out.println("\n------------------ JAXB PARSER ------------------");
        Parser.parseXMLWithJAXB(file);

        //Jackson
        System.out.println("\n----------------- JACKSON PARSER -----------------");
        Parser.parseJSONWithJackson(jsonFile);

    }
}