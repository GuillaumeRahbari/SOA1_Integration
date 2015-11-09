package fr.unice.polytech.soa1.shop3000.flows.pay;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.codehaus.jettison.json.*;


/**
 * Created by user on 26/10/2015.
 */
public class CheckClientExistenceVolley extends SuperProcessor{

    public void process(Exchange exchange) throws Exception {
        String loginToTest = (String)exchange.getProperty("login");
        String body = extractExchangeBody(exchange);
        String login = new JSONObject(body).getString("login");
        if(loginToTest.equals(login)) {
            exchange.setProperty("result", true);
        }else {
            exchange.setProperty("result", false);
        }
    }
}
