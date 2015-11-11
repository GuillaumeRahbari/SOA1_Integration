package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by user on 02/11/2015.
 */
public class CreateClientInShopsFlow extends RouteBuilder {

    private CreateClientBiko createClientBiko;
    private CreateClientBeer createClientBeer;
    private CreateClientVolley createClientVolley;

    public CreateClientInShopsFlow(){
        this.createClientBiko = new CreateClientBiko();
        this.createClientBeer = new CreateClientBeer();
        this.createClientVolley = new CreateClientVolley();
    }

    @Override
    public void configure() throws Exception {
        from(Endpoint.CREATE_CLIENT_BIKO.getInstruction())
                .log("Begin create biko client")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setBody(constant(""))
                //.setProperty("username",constant("test"))
                        /** @(Link CreateClientBiko} **/
                .process(createClientBiko)
                .recipientList(simple("http://localhost:8181/cxf/biko/clients?bridgeEndpoint=true"))
                .to(Endpoint.ADD_TO_CART_BIKO.getInstruction());


        from(Endpoint.CREATE_CLIENT_ALL_HAIL_BEER.getInstruction())
                .log("Begin create beer client")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setBody(constant(""))
                //.setProperty("username",constant("nab"))
                //.setProperty("password",constant("nab"))
                        /** @(Link CreateClientBeer } **/
                .process(createClientBeer)
                .recipientList(simple("http://localhost:8181/cxf/account?bridgeEndpoint=true"))
                .to(Endpoint.ADD_TO_CART_ALL_HAIL_BEER.getInstruction());

        from(Endpoint.CREATE_CLIENT_VOLLEY_ON_THE_BEACH.getInstruction())
                .log("Begin create volley client")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setBody(constant(""))
                //.setProperty("login",constant("nab"))
                //.setProperty("password",constant("nab"))
                        /** @(Link CreateClientVolley} **/
                .process(createClientVolley)
                .recipientList(simple("http://localhost:8181/cxf/volley/accounts?bridgeEndpoint=true"))
                .to(Endpoint.ADD_TO_CART_VOLLEY_ON_THE_BEACH.getInstruction());
    }
}
