package fr.unice.polytech.soa1.shop3000.flows.clientfile;

import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by guillaume on 26/10/2015.
 */
public class CheckClientInDatabase implements Processor {


    /**
     * Check if the client exists in the db.
     *
     * @param exchange A client contains a firstName, lastName, and a cart.
     * @throws Exception
     */
    public void process(Exchange exchange) throws Exception {
        // We get a client by the "client" property
        Client client = (Client)exchange.getProperty("client");

        // We check if he exists in db.
        if (ClientStorage.checkInDB(client)) {
            // if he is, we set the property "client" to null.
            exchange.setProperty("client", null);
        }
        // else we do nothing to have the "client" property equal to the client.
    }
}
