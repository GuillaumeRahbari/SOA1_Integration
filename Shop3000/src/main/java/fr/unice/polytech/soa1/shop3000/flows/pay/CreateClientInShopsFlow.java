package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by user on 02/11/2015.
 */
public class CreateClientInShopsFlow extends RouteBuilder {

    private CreateBikoClient createClientBiko;
    private CreateBeerClient createClientBeer;
    private CreateVolleyClient createClientVolley;

    public CreateClientInShopsFlow(){
        this.createClientBiko = new CreateBikoClient();
        this.createClientBeer = new CreateBeerClient();
        this.createClientVolley = new CreateVolleyClient();
    }

    @Override
    public void configure() throws Exception {
        from(Endpoint.CREATE_CLIENT_BIKO.getInstruction())
                .log("Begin create biko client")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setBody(constant(""))
                        /** @(Link CreateClientBiko} **/
                .process(createClientBiko)
                .recipientList(simple("http://localhost:8181/cxf/biko/clients?bridgeEndpoint=true"));


        from(Endpoint.CREATE_CLIENT_ALL_HAIL_BEER.getInstruction())
                .log("Begin create beer client")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setBody(constant(""))
                        /** @(Link CreateClientBeer } **/
                .process(createClientBeer)
                .recipientList(simple("http://localhost:8181/cxf/account?bridgeEndpoint=true"));

        from(Endpoint.CREATE_CLIENT_VOLLEY_ON_THE_BEACH.getInstruction())
                .log("Begin create volley client")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setBody(constant(""))
                        /** @(Link CreateClientVolley} **/
                .process(createClientVolley)
                .recipientList(simple("http://localhost:8181/cxf/volley/accounts?bridgeEndpoint=true"));
    }


    private class CreateVolleyClient extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String login = (String)exchange.getProperty("clientID");
            //String password = (String)exchange.getProperty("password");
            JSONObject jObject = new JSONObject();
            jObject.put("name", login);
            jObject.put("password", login);
            //System.out.println(jObject.toString());
            exchange.getIn().setBody(jObject.toString());
        }
    }

    private class CreateBikoClient extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String username = (String) exchange.getProperty("clientID");


            JSONObject jObject = new JSONObject();
            jObject.put("name", username);
            jObject.put("id", 1);
            //System.out.println(jObject.toString());
            exchange.getIn().setBody(jObject.toString());
        }
    }

    private class CreateBeerClient extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String login = (String)exchange.getProperty("clientID");
            //String password = (String)exchange.getProperty("password");

            JSONObject jObject = new JSONObject();
            jObject.put("name", login);
            jObject.put("password", login);
            //System.out.println(jObject.toString());
            exchange.getIn().setBody(jObject.toString());
        }
    }
}
