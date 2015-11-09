package fr.unice.polytech.soa1.shop3000.flows.pay;

import com.fasterxml.jackson.core.JsonGenerator;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.utils.Shop;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;

import java.util.HashMap;
import java.util.List;

/**
 * @author Laureen Ginier
 * {@link AddItemsToCarts}
 */
public class MyProcessor extends SuperProcessor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String clientID = (String)exchange.getProperty("clientID");
        Client client = ClientStorage.read(clientID);
        if(client != null) {
            HashMap<String, List<CatalogItem>> cart = client.getCart(); //<ShopName, List<CatalogItem>>
            List<CatalogItem> beerItems = cart.get(Shop.BEER.getName());
            JsonGenerator jgen;
        }
        /**
         *
         {
             "GuiGui": {
             "owner": "GuiGui",
             "cartData": {
             "Leffe": 5.0
            }
            },
             "GuiGui": {
             "owner": "GuiGui",
             "cartData": {
             "Leffe": 18.0
            }
            },
             "admin": {
             "owner": "admin",
             "cartData": {
             "Leffe": 18.0,
             "GuiBeer": 21.0,
             "YoBeer": 24.0
            }
            }
         }
         */
    }
}
