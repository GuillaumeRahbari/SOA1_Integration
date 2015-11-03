package fr.unice.polytech.soa1.shop3000.utils;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Marc Karassev
 *
 * Abstract class for class implementing Processor in order to provide a final method which goal is to convert an
 * InputStream into a String for extracting an Exchange object's body into a String for example.
 */
public abstract class SuperProcessor implements Processor {

    /**
     * Converts an Exchange object's body into a String.
     *
     * @param exchange the Exchange object from which the body is to be converted
     * @return the resulting string
     */
    public final String extractExchangeBody(Exchange exchange) {
        return getStringFromInputStream((InputStream) exchange.getIn().getBody());
    }

    /**
     * Converts an Exchange object's property into a String.
     *
     * @param exchange the Exchange object from which a property is to be converted
     * @param property the Exchange object's property to convert
     * @return the resulting string
     */
    public final String extractExchangeProperty(Exchange exchange, String property) {
        return getStringFromInputStream((InputStream) exchange.getProperty(property));
    }

    /**
     * Converts an InputStream into a String.
     *
     * @param inputStream the InputStream to convert
     * @return the resulting String
     */
    public final String getStringFromInputStream(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder out = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) { out.append(line); }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    /**
     * Cast an Exchange object's body (that is exclusively a boolean) into a real boolean.
     * @param exchange
     * @return A boolean object of the Exchange object's body.
     */
    public final boolean extractBodyFromExchangeBool(Exchange exchange){
        return (Boolean) exchange.getIn().getBody();
    }

    public abstract void process(Exchange exchange) throws Exception;
}
