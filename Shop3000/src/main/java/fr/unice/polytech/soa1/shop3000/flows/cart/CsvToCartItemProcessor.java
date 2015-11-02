package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Map;

/**
 *This class is used to convert a Map containing on line of the CSV input file and
 * convert it to a CatalogItem
 */
public class CsvToCartItemProcessor implements Processor{

    /**
     * Create a new CatalogItem matching with the data in the Map contained by the Body
     * and sets the Body with the new CatalogItem.
     *
     * @param exchange must contain a Map<String,Object> as body
     * @throws Exception
     */
    public void process(Exchange exchange) throws Exception {

        Map<String,Object> bodyData = (Map<String, Object>) exchange.getIn().getBody();

        CatalogItem catalogItem = buildCatalogItem(bodyData);

        exchange.getIn().setBody(catalogItem);

    }

    private CatalogItem buildCatalogItem(Map<String, Object> data){
        CatalogItem catalogItem = new CatalogItem();

        catalogItem.setName((String) data.get("name"));
        catalogItem.setDescription((String) data.get("description"));
        catalogItem.setPrice(Double.parseDouble((String) data.get("price")));

        return catalogItem;
    }
}
