package fr.unice.polytech.soa1.shop3000.flows.catalog;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Quentin on 10/21/2015.
 * Read the response, formats it into a json string and add the shop name
 */
public class ReadResponseStream implements Processor {

    private String shopName;

    public ReadResponseStream(String shopName){
        this.shopName = shopName;
    }

    /**
     * This method take the body of the exchange andd add the name of the shop to create several JSON Object
     * {"shopName":"A",Items:[....]}
     * {"shopName":"B",Items:[....]}
     * {"shopName":"C",Items:[....]}
     *
     * This work is in progress we want to put the JSON OBjects in a JSONArray
     *
     * @param exchange
     * @throws Exception
     */
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
