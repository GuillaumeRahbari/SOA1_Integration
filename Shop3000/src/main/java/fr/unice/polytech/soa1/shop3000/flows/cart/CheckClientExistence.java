package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.codehaus.jettison.json.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Nabil on 26/10/2015.
 */
public class CheckClientExistence implements Processor {

    private String derp;

    public CheckClientExistence(String derp){
        this.derp = derp;
    }

    public void process(Exchange exchange) throws Exception {
        //test if client exist
        InputStream response = (InputStream) exchange.getIn().getBody();
        String nameToTest = (String)exchange.getProperty("clientID");
        System.out.println(nameToTest);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { out.append(line); }
        out.append("}");
        reader.close();

        JSONObject jObject = new JSONObject(out.toString());
        String name = jObject.getString("name");

        System.out.println(out.toString());
        System.out.println(name);
        if(nameToTest.equals(name))
            exchange.setProperty("result",true);
        else
            exchange.setProperty("result",false);

        /**
        Client client = exchange.getIn().getBody(Client.class);
        System.out.println(client.getFirstName());**/
    }
}
