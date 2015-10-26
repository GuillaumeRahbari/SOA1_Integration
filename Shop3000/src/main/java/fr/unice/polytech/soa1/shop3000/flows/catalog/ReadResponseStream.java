package fr.unice.polytech.soa1.shop3000.flows.catalog;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Quentin on 10/21/2015.
 */
public class ReadResponseStream implements Processor {

    private String shopName;

    public ReadResponseStream(String shopName){
        this.shopName = shopName;
    }

    public void process(Exchange exchange) throws Exception {
        InputStream response = (InputStream) exchange.getIn().getBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder out = new StringBuilder();
        out.append("{\"shopName\":\""+shopName+"\",\"items\":");
        String line;
        while ((line = reader.readLine()) != null) { out.append(line); }
        out.append("}");
        reader.close();
        exchange.getIn().setBody(out.toString());
    }
}
