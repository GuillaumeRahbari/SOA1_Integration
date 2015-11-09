package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.codehaus.jettison.json.*;

/**
 * Created by Nabil on 26/10/2015.
 */
public class CheckClientExistenceBiko extends SuperProcessor {

    public CheckClientExistenceBiko(){
    }

    public void process(Exchange exchange) throws Exception {
        //test if client exist
        String nameToTest = (String)exchange.getProperty("clientID");
        String body = extractExchangeBody(exchange);
        JSONObject jObject = new JSONObject(body);
        String name = jObject.getString("name");

        if(nameToTest.equals(name))
            exchange.setProperty("result",true);
        else
            exchange.setProperty("result",false);

    }
}
