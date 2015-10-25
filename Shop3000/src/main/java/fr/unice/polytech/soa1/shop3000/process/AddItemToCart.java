package fr.unice.polytech.soa1.shop3000.process;

import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.Client;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by Quentin on 10/25/2015.
 */
public class AddItemToCart implements Processor {


    public void process(Exchange exchange) throws Exception {
        Client client = exchange.getIn().getBody(Client.class);
        CatalogItem catalogItem = exchange.getIn().getBody(CatalogItem.class);

        client.getCart().getCatalogItemList().add(catalogItem);
    }
}
