package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by tom on 02/11/15.
 */
public class CsvToCartItemProcessor implements Processor{

    public void process(Exchange exchange) throws Exception {

        Map<String,Object> bodyData = (Map<String, Object>) exchange.getIn().getBody();

        CatalogItem catalogItem = buildCatalogItem(bodyData);

        exchange.getIn().setBody(catalogItem);

    }

    private CatalogItem buildCatalogItem(Map<String, Object> data){
        System.out.println("\nNew CatalogItem  :\n");
        CatalogItem catalogItem = new CatalogItem();

        System.out.println("\nDATA : " + data);

        catalogItem.setName((String) data.get("name"));
        catalogItem.setDescription((String) data.get("description"));
        System.out.println("\nDouble : " + Double.parseDouble((String) data.get("price")));
        catalogItem.setPrice(Double.parseDouble((String) data.get("price")));

        System.out.println("\nITEM : " + catalogItem);

        return catalogItem;
    }
}
