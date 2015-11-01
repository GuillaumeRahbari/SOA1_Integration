package fr.unice.polytech.soa1.shop3000.flows.clientfile;

import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author Quentin Cornevin
 *
 * This process add a client to the database
 */
public class AddClientToDataBase implements Processor {

    /**
     * This method add the client in the database.
     *
     * @param exchange
     * @throws Exception
     */
    public void process(Exchange exchange) throws Exception {
        // We get a client by the "client" property
        Client client = (Client)exchange.getProperty("client");
        // We add the client in the db.
        ClientStorage.addClient(client);
        // We set the body to tell the client is added.
        exchange.getIn().setBody("Client added to database");
    }
}
