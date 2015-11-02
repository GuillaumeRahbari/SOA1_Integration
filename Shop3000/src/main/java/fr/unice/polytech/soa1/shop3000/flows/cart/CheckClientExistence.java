package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.codehaus.jettison.json.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Nabil on 26/10/2015.
 */
public class CheckClientExistence extends SuperProcessor {

    private String derp;

    public CheckClientExistence(String derp){
        this.derp = derp;
    }

    public void process(Exchange exchange) throws Exception {
        //test if client exist
        String nameToTest = (String)exchange.getProperty("clientID");
        String body = extractBodyFromExchange(exchange);
        JSONObject jObject = new JSONObject(body);
        String name = jObject.getString("name");

        if(nameToTest.equals(name))
            exchange.setProperty("result",true);
        else
            exchange.setProperty("result",false);

    }
}
