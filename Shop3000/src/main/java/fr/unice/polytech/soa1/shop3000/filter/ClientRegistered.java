package fr.unice.polytech.soa1.shop3000.filter;

import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import org.apache.camel.Exchange;


/**
 * This Class allow to filtrer the use who are already registered our system or not.
 * This class will handle more filter in the future
 *
 */
public class ClientRegistered  {

    /**
     * Filter if the client in the body of the exchange is in the database
     * @param exchange
     * @return true if the client is not in the database, false otherwise.
     */
    public boolean filter(Exchange exchange) {
        Client client = exchange.getIn().getBody(Client.class);
        return !(ClientStorage.checkInDB(client));
    }
}
