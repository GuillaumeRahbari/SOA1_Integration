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
     * Body mocked with a fake client
     * @param exchange
     * @throws Exception
     */
    public void process(Exchange exchange) throws Exception {
        Client goodClient = new Client(MockedData.CLIENT_FIRST_NAME,MockedData.CLEINT_LAST_NAME);
        exchange.getIn().setBody(goodClient);
    }
}
