package fr.unice.polytech.soa1.shop3000.filter;

import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import org.apache.camel.Exchange;


/**
 * Created by Quentin on 10/19/2015.
 */
public class ClientRegistered  {

    public boolean filter(Exchange exchange) {
        Client client = exchange.getIn().getBody(Client.class);
        return ClientStorage.checkInDB(client);
    }
}
