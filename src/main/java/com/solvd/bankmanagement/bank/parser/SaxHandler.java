package com.solvd.bankmanagement.bank.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println("Start Element: " + qName);
        for (int i = 0; i < attributes.getLength(); i++) {
            System.out.println("Attribute: " + attributes.getQName(i) + "=" + attributes.getValue(i));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        System.out.println("End Element: " + qName);
    }

    @Override
    public void characters(char[] ch, int start, int length){
        String content = new String(ch, start, length).trim();
        if (!content.isEmpty()) {
            System.out.println("Character Data: " + content);
        }
    }
}
