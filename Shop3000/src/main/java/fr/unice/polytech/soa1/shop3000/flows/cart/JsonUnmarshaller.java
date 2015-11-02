package fr.unice.polytech.soa1.shop3000.flows.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by guillaume on 02/11/2015.
 */
public class JsonUnmarshaller extends SuperProcessor {


    @Override
    public void process(Exchange exchange) throws Exception {
        String body = extractBodyFromExchange(exchange);
        ObjectMapper mapper = new ObjectMapper();
        CatalogItem catalogItem = mapper.readValue(body, CatalogItem.class);
        exchange.setProperty("item", catalogItem);
    }
}
