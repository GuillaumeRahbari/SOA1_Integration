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


    /**
     * This process unmarshalls the json body and creates a property item with the object.
     * @param exchange This Exchange object has a body containing a CatalogItem.
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        // Read the exchange body.
        String body = extractExchangeBody(exchange);
        // Mapping of the body into the object CatalogItem.
        ObjectMapper mapper = new ObjectMapper();
        CatalogItem catalogItem = mapper.readValue(body, CatalogItem.class);
        // Set of the item property with the CatalogItem object
        exchange.setProperty("item", catalogItem);
    }
}
