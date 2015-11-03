package fr.unice.polytech.soa1.shop3000.flows.cart;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.InputStream;

/**
 * Created by Quentin on 11/1/2015.
 */
public class JsonToItem implements Processor {


    public void process(Exchange exchange) throws Exception {
        InputStream response = (InputStream) exchange.getIn().getBody();
        String test = exchange.getIn().getBody(String.class);
        System.out.println(test);
/*        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder out = new StringBuilder();
        String line;
        System.out.println("derp :" + reader.readLine());
        while ((line = reader.readLine()) != null) {
            System.out.println("coucou");
            out.append(line);
        }
        System.out.println("JE fais un putin de test coucou" + out + "derp");
        reader.close();*/
    }
}
