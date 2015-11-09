package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by user on 03/11/2015.
 */
public class CreateClientVolley extends SuperProcessor{
    @Override
    public void process(Exchange exchange) throws Exception {
        String login = (String)exchange.getProperty("login");
        String password = (String)exchange.getProperty("password");


        JSONObject jObject = new JSONObject();
        jObject.put("name", login);
        jObject.put("password", password);
        System.out.println(jObject.toString());
        exchange.getIn().setBody(jObject.toString());
    }
}