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
 * Abstract class for class implementing Processor in order to provide a static method which goal is to convert an
 * Exchange object's body into a String.
 */
public abstract class SuperProcessor implements Processor {

    /**
     * Converts a an Exchange object's body into a String.
     *
     * @param exchange the Exchange object to convert
     * @return the resulting String
     */
    public final String extractBodyFromExchange(Exchange exchange) {
        InputStream response = (InputStream) exchange.getIn().getBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
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

    public abstract void process(Exchange exchange) throws Exception;
}
