package com.solvd.bankmanagement.bank.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.bankmanagement.bank.domain.Account;
import com.solvd.bankmanagement.bank.domain.Bank;
import com.solvd.bankmanagement.bank.domain.Customer;
import com.solvd.bankmanagement.bank.domain.Employee;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;


public class Parser {

    public static void parseXMLWithSAX(File file) {
        try {
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            SAXParser parser = saxFactory.newSAXParser();
            parser.parse(file, new SaxHandler());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parseXMLWithJAXB(File file){
        try {
            JAXBContext context = JAXBContext.newInstance(Bank.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Bank bank = (Bank) unmarshaller.unmarshal(file);
            processBankObject(bank);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parseJSONWithJackson(File file){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Bank bank = mapper.readValue(file, Bank.class);
            processBankObject(bank);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void processBankObject(Bank bank) {
        if (bank != null) {
            System.out.println("Bank Name: " + bank.getName());
            System.out.println("Bank AddressId: " + bank.getAddressId());

            if (bank.getEmployees() != null) {
                for (Employee employee : bank.getEmployees()) {
                    System.out.println("Employee Name: " + employee.getFirstName() + " " + employee.getLastName());
                }
            }

            if (bank.getCustomers() != null) {
                for (Customer customer : bank.getCustomers()) {
                    System.out.println("Customer Name: " + customer.getFirstName() + " " + customer.getLastName());

                    if (customer.getAccounts() != null) {
                        for (Account account : customer.getAccounts()) {
                            System.out.println("Account Type: " + account.getType());
                        }
                    }
                }
            }
        }
    }


//    public static void parseXMLWithDOM(File file) {
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(file);
//            document.getChildNodes();
//        } catch (ParserConfigurationException | IOException | SAXException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public static void parseXMLWithStAX(File file) {
//        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
//        try (FileInputStream fileInputStream = new FileInputStream(file)) {
//            XMLEventReader reader = xmlInputFactory.createXMLEventReader(fileInputStream);
//            while(reader.hasNext()) {
//                XMLEvent event = reader.nextEvent();
//            }
//        } catch (IOException | XMLStreamException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
