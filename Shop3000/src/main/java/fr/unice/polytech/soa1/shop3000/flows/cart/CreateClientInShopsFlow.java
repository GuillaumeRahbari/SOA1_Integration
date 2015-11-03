package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by user on 02/11/2015.
 */
public class CreateClientInShopsFlow extends RouteBuilder {

    private CreateClientBiko createClientBiko;

    public CreateClientInShopsFlow(){
        this.createClientBiko = new CreateClientBiko();
    }

    @Override
    public void configure() throws Exception {
        from(Endpoint.CREATE_CLIENT_BIKO.getInstruction())
                .log("Begin create biko client")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setBody(constant(""))
                .setProperty("username",constant("test"))
                .process(createClientBiko)
                .recipientList(simple("http://localhost:8181/cxf/biko/clients?bridgeEndpoint=true"));

        from(Endpoint.CREATE_CLIENT_ALL_HAIL_BEER.getInstruction())
                .log("Begin create beer client")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setBody(constant(""))
                .setProperty("username",constant("nab"))
                .setProperty("password",constant("nab"))
                .process(createClientBiko)
                .recipientList(simple("http://localhost:8181/cxf/account?bridgeEndpoint=true"));
    }
}
