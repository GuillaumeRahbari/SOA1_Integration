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

        Client client = (Client)exchange.getProperty("client");
        CatalogItem catalogItem = (CatalogItem)exchange.getProperty("item");
        String shopName = (String)exchange.getProperty("shopName");

        if (client.getCart().get(shopName)==null) {
            client.getCart().put(shopName, new ArrayList<CatalogItem>());
        }
        client.getCart().get(shopName).add(catalogItem);
    }
}
