package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by user on 02/11/2015.
 */
public class CreateClientBiko extends SuperProcessor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String body = extractExchangeBody(exchange);
        String username = extractExchangeProperty(exchange,"username");

        JSONObject jObject = new JSONObject();
        jObject.append("id",1);
        jObject.append("name",username);
        jObject.append("cart","");

        exchange.getIn().setBody(jObject.toString());
    }
}
