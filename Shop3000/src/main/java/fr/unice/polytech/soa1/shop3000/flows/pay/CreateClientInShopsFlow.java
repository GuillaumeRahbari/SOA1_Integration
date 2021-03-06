package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.flows.pay.defs.PayProperties;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author Nabil El Moussaid
 * This class contains the flow that create accounts in providers
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
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setBody(constant(""))
                        /** @(Link CreateClientBeer } **/
                .process(createClientBeer)
                .recipientList(simple("http://localhost:8181/cxf/shop/account?bridgeEndpoint=true"));


        from(Endpoint.CREATE_CLIENT_VOLLEY_ON_THE_BEACH.getInstruction())
                .log("Begin create volley client")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setBody(constant(""))
                        /** @(link CreateClientVolley} **/
                .process(createClientVolley)
                .recipientList(simple("http://localhost:8181/cxf/volley/accounts?bridgeEndpoint=true"));
    }


    private class CreateVolleyClient extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String login = (String)exchange.getProperty("clientID");
            Client client = ClientStorage.read(login);

            JSONObject jObject = new JSONObject();
            jObject.put("login", client.getFirstName());
            jObject.put("password", client.getLastName());
            exchange.getIn().setBody(jObject.toString());
        }
    }

    private class CreateBikoClient extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String username = (String) exchange.getProperty(PayProperties.CLIENT_ID_PROPERTY.getInstruction());
            Client client = ClientStorage.read(username);

            JSONObject jObject = new JSONObject();
            jObject.put("name", client.getLastName());
            jObject.put("id", client.getBikoId());
            exchange.getIn().setBody(jObject.toString());
        }
    }

    private class CreateBeerClient extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String login = (String)exchange.getProperty(PayProperties.CLIENT_ID_PROPERTY.getInstruction());
            //String password = (String)exchange.getProperty("password");
            Client client = ClientStorage.read(login);

            JSONObject jObject = new JSONObject();
            jObject.put("username", client.getFirstName());
            jObject.put("password", client.getLastName());
            exchange.getIn().setBody(jObject.toString());
        }
    }
}
