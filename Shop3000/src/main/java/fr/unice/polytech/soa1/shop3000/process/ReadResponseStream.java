package fr.unice.polytech.soa1.shop3000.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Quentin on 10/21/2015.
 */
public class ReadResponseStream implements Processor {

    public void process(Exchange exchange) throws Exception {
        InputStream response = (InputStream) exchange.getIn().getBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { out.append(line); }
        reader.close();
        exchange.getIn().setBody(out.toString());
    }
}
