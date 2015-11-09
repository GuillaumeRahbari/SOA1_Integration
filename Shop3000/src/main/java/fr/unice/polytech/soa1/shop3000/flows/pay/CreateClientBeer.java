package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by user on 03/11/2015.
 */
public class CreateClientBeer extends SuperProcessor {
    @Override
    public void process(Exchange exchange) throws Exception {

        String login = (String)exchange.getProperty("clientID");
        //String password = (String)exchange.getProperty("password");

        JSONObject jObject = new JSONObject();
        jObject.put("name", login);
        jObject.put("password", login);
        //System.out.println(jObject.toString());
        exchange.getIn().setBody(jObject.toString());
    }
}
