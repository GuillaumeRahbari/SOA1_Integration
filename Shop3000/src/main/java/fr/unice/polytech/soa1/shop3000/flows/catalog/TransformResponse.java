package fr.unice.polytech.soa1.shop3000.flows.catalog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import fr.unice.polytech.soa1.shop3000.business.Catalog;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * @author Laureen Ginier
 * Read the response, which is a json array of items from a shop, and formats it into a json string
 * containing the origin shop's name and the list of standardized CatalogItems.
 */
public abstract class TransformResponse extends SuperProcessor {

    protected String shopName;

    public TransformResponse(String shopName){
        this.shopName = shopName;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
		String out = extractExchangeBody(exchange);

        ObjectMapper mapper = new ObjectMapper();
        List<CatalogItem> items = mapToCatalogItem(out);
        //TODO fill in the 3 lists of the catalog
        /*String jsonString = "{\"shopName\":\""+shopName+"\", \"items\":"
                + mapper.writeValueAsString(items) + "}";
        exchange.getIn().setBody(jsonString);*/
        String jsonString = mapper.writeValueAsString(items);
        exchange.getIn().setBody(jsonString.replace("[","").replace("]",""));
    }

    public abstract List<CatalogItem> mapToCatalogItem(String jsonString) throws Exception;
}
