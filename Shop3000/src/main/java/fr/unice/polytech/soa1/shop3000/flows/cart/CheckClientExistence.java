package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by Nabil on 26/10/2015.
 */
public class CheckClientExistence implements Processor {

    private String derp;

    public CheckClientExistence(String derp){
        this.derp = derp;
    }

    public void process(Exchange exchange) throws Exception {
        Client client = exchange.getIn().getBody(Client.class);




        exchange.getIn().setHeader("result", ClientStorage.checkInDB(client));
    }
}
