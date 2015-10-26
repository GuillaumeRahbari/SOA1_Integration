package fr.unice.polytech.soa1.shop3000.mock;

import fr.unice.polytech.soa1.shop3000.business.Client;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author Quentin Corneivn
 *
 * This class mock the creation of a client in the body of the message
 */
public class ClientFileMock implements Processor {

    /**
     * In this method we get a validate client with the mock.
     * And we set the "client" property with this validate client.
     *
     * @param exchange Empty.
     * @throws Exception
     */
    public void process(Exchange exchange) throws Exception {
        // We get a client from the mock file.
        Client goodClient = new Client(MockedData.CLIENT_FIRST_NAME,MockedData.CLEINT_LAST_NAME);
        // We set the "client" property with the client we got from the mock.
        exchange.setProperty("client", goodClient);
    }
}
