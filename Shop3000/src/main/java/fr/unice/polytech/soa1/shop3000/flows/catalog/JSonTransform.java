package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.codehaus.jettison.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Laureen Ginier
 * Encapsulate the exchange into '[]' to have a valid json
 */
public class JSonTransform implements Processor {

    public void process(Exchange exchange) throws Exception {
        String response = (String) exchange.getIn().getBody();
        String jsonResponse = "[" + response + "]";
        exchange.getIn().setBody(jsonResponse);
    }
}
