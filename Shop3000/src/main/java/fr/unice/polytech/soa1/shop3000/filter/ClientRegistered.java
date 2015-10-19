package fr.unice.polytech.soa1.shop3000.filter;

import org.apache.camel.Exchange;


/**
 * Created by Quentin on 10/19/2015.
 */
public class ClientRegistered  {

    public boolean filter(Exchange exchange) {
        return true;
    }
}
