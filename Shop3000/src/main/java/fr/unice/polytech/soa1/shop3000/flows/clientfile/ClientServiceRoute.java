package fr.unice.polytech.soa1.shop3000.flows.clientfile;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/24/2015.
 */
public class ClientServiceRoute extends RouteBuilder{


    /**
     * Root definitions for client.
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");

        /**
         * This flow create a client in the shop3000 database.
         *
         * This flow continues to {@link Unmarshaller#jsonToClient}.
         */
        rest("/clientFile")
                .post()
                .to(Endpoint.CLIENT_UNMARSHALL.getInstruction());

        /**
         * This flows get a client in the shop3000 database thanks to the users's first name.
         *
         * This flow continues to {@link ClientFileFlows#configure()}.
         */
        rest("/clientFile/{clientFirstName}")
                .get()
                .to(Endpoint.GET_CLIENT_FILE.getInstruction());

        /**
         * This flows delete a client in the shop3000 database thanks to the user's first name.
         *
         * This flow continues to {@link ClientFileFlows#configure()}.
         */
        rest("/clientFile/{clientFirstName}")
                .delete()
                .to(Endpoint.DELETE_CLIENT_FILE.getInstruction());

        /**
         * The flows to send status.
         */
        from(Endpoint.SEND_STATUS.getInstruction())
                .log("Status : " + "${property.status}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("${property.status}"));
    }
}
