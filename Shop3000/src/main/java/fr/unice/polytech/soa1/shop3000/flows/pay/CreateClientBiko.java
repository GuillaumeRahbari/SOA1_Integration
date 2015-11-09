package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by user on 02/11/2015.
 */
public class CreateClientBiko extends SuperProcessor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String username = (String) exchange.getProperty("clientID");


        JSONObject jObject = new JSONObject();
        jObject.put("name", username);
        jObject.put("id", 1);
        //System.out.println(jObject.toString());
        exchange.getIn().setBody(jObject.toString());
    }
}
