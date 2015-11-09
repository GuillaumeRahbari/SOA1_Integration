package fr.unice.polytech.soa1.shop3000.flows.clientfile;

import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by guillaume on 26/10/2015.
 */
public class GetClientInDatabase implements Processor {


    /**
     * Get the client in the db.
     *
     * @param exchange This object contains at least a clientFirstName property.
     * @throws Exception
     */
    public void process(Exchange exchange) throws Exception {

        // We get the clientFirstName property.
        String clientFirstName = (String)exchange.getProperty("clientFirstName");

        // We set the databaseClient property with the value of the getting client.
        exchange.setProperty("databaseClient",ClientStorage.read(clientFirstName));
    }
}
