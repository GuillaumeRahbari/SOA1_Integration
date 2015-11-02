package fr.unice.polytech.soa1.shop3000.flows.cart;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import jdk.nashorn.api.scripting.JSObject;
import org.apache.camel.Exchange;
import org.codehaus.jettison.json.*;
import org.apache.camel.Processor;


/**
 * Created by user on 26/10/2015.
 */
public class CheckClientExistenceVolley extends SuperProcessor{

    public void process(Exchange exchange) throws Exception {
        String loginToTest = (String)exchange.getProperty("login");
        String body = extractBodyFromExchange(exchange);
        String login = new JSONObject(body).getString("login");
        if(loginToTest.equals(login)) {
            exchange.setProperty("result", true);
        }else {
            exchange.setProperty("result", false);
        }
    }
}
