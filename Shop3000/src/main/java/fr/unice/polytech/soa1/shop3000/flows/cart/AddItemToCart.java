package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.Client;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;

/**
 * Created by Quentin on 10/25/2015.
 */
public class AddItemToCart implements Processor {


    public void process(Exchange exchange) throws Exception {

        Client client = exchange.getIn().getBody(Client.class);
        if (client != null) {
            System.out.println("Got a client : " + client);
        }else System.out.println("No client");
        CatalogItem catalogItem = exchange.getIn().getBody(CatalogItem.class);
        if (catalogItem != null) {
            System.out.printf("Got a catalogItem : " + catalogItem);
        }else System.out.println("No catalogItem");


        String shop = "ShopTest";//exchange.getIn();

        if (client.getCart().get(shop)==null) {
            client.getCart().put(shop, new ArrayList<CatalogItem>());
        }
        client.getCart().get(shop).add(catalogItem);
    }
}
