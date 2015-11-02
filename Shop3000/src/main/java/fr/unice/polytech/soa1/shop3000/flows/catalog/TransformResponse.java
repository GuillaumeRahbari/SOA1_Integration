package fr.unice.polytech.soa1.shop3000.flows.catalog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Laureen Ginier
 * Read the response and formats it into a json string with a list of CatalogItem
 */
public class TransformResponse implements Processor {

    protected String shopName;

    public TransformResponse(String shopName){
        this.shopName = shopName;
    }

    public void process(Exchange exchange) throws Exception {
        InputStream response = (InputStream) exchange.getIn().getBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { out.append(line); }
        reader.close();

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<CatalogItem> items = mapper.readValue(out.toString(),
                new TypeReference<List<CatalogItem>>() { } );

        String jsonString = "{\"shopName\":\""+shopName+"\", \"items\":"
                + mapper.writeValueAsString(items) + "}";
        exchange.getIn().setBody(jsonString);
    }
}
