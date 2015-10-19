package fr.unice.polytech.soa1.shop3000.process.mock;

import fr.unice.polytech.soa1.shop3000.business.Client;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by Quentin on 10/19/2015.
 */
public class ClientFileMock implements Processor {

    public void process(Exchange exchange) throws Exception {
        Client goodClient = new Client(MockedData.CLIENT_FIRST_NAME,MockedData.CLIENT_FIRST_NAME);
        exchange.getIn().setBody(goodClient);
    }
}
