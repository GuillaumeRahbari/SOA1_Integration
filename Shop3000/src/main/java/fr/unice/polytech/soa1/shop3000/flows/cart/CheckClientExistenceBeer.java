package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.codehaus.jettison.json.JSONArray;

/**
 * Created by user on 26/10/2015.
 */
public class CheckClientExistenceBeer extends SuperProcessor {
    public void process(Exchange exchange) throws Exception {
        // test if client exist
        String body = extractExchangeBody(exchange);
        String loginToTest = (String)exchange.getProperty("login");
        String login = new JSONArray(body).getJSONObject(0).getJSONObject(loginToTest).getString("username");

        if(loginToTest.equals(login)) {
            exchange.setProperty("result", true);
        }else {
            exchange.setProperty("result", false);
        }
    }
}
