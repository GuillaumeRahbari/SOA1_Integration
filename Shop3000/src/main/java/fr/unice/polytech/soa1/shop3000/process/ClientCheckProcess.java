package fr.unice.polytech.soa1.shop3000.process;

import fr.unice.polytech.soa1.shop3000.business.Client;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by Quentin on 10/19/2015.
 */
public class ClientCheckProcess implements Processor {


    public void process(Exchange exchange) throws Exception {
        Client goodClient = new Client("Good","Boy");
/*        Client badClient = new Client("Bad","Boy");
        List<Client> clientList = new ArrayList<Client>();
        clientList.add(goodClient);
        clientList.add(badClient);*/
        exchange.getIn().setBody(goodClient);

/*
        Map<String, Object> input = (Map<String, Object>) exchange.getIn().getBody();
*/
    }
}
