package fr.unice.polytech.soa1.shop3000.flows.clientfile;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;


/**
 * Created by Quentin on 11/9/2015.
 */
public class Unmarshaller extends RouteBuilder {

    private JsonToClient jsonToClient;

    public Unmarshaller() {
        this.jsonToClient = new JsonToClient();
    }

    @Override
    public void configure() throws Exception {
        from(Endpoint.CLIENT_UNMARSHALL.getInstruction())
            .log("Avant le process")
            .process(jsonToClient)
            .log("Après le process")
            .to(Endpoint.CREATE_CLIENT_FILE.getInstruction());
    }

    private class JsonToClient extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String body = extractExchangeBody(exchange);
            System.out.println(body);
            ObjectMapper mapper = new ObjectMapper();
            Client client = mapper.readValue(body,Client.class);
            System.out.println(client);
            exchange.setProperty("client",client);
        }
    }


}
