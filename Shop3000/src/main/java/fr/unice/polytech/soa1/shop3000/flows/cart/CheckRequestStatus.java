package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by guillaume on 02/11/2015.
 */
public class CheckRequestStatus extends SuperProcessor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Boolean body = extractBodyFromExchangeBool(exchange);
        if (body) {
            exchange.setProperty("status", 200);
        }
        else {
            exchange.setProperty("status", 400);
        }
        exchange.getIn().setBody(null);
    }
}
