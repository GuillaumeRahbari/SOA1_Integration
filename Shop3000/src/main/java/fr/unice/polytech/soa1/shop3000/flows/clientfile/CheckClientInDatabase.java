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
     * @param exchange A client containing a firstName, lastName, and a cart.
     * @throws Exception
     */
    public void process(Exchange exchange) throws Exception {
        Client client = (Client)exchange.getProperty("client");
        if (ClientStorage.checkInDB(client)) {
           exchange.getIn().setBody(new Client(null, null));
        }
        else {
            exchange.getIn().setBody(client);
        }
    }
}
