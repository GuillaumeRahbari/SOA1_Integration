package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by guillaume on 02/11/2015.
 */
public class CheckRequestStatus extends SuperProcessor {

    /**
     * This process look the message into the body in the exchange object and set the status property
     * to the status code adapated.
     * @param exchange The object exchange containing a body with the boolean :
     *                 <ul>
     *                  <li>true</li>
     *                  <li>false</li>
     *                 </ul>
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        // We get the boolean into the body.
        Boolean body = extractBodyFromExchangeBool(exchange);
        // We set the status properly.
        if (body) {
            exchange.setProperty("status", 200);
        }
        else {
            exchange.setProperty("status", 400);
        }
        // We empty the body.
        exchange.getIn().setBody(null);
    }
}
