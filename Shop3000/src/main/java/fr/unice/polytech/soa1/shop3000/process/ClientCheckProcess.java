package fr.unice.polytech.soa1.shop3000.process;

import fr.unice.polytech.soa1.shop3000.business.Client;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author Quentin Cornevin
 *
 * TODO !
 */
public class ClientCheckProcess implements Processor {


    public void process(Exchange exchange) throws Exception {
        Client goodClient = new Client("Good","Boy");
        exchange.getIn().setBody(goodClient);

    }
}
