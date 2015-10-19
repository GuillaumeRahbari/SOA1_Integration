package fr.unice.polytech.soa1.shop3000.flows;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by Quentin on 10/19/2015.
 */
public class CheckClientDB implements Processor {

    public void process(Exchange exchange) throws Exception {
  //     Map<String, Object> input = (Map<String, Object>) exchange.getIn().getBody();
        System.out.println("In process");
    }
}
