package fr.unice.polytech.soa1.shop3000.flows.clientfile;

import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Quentin Cornevin
 *
 * This class represent the flow which handle the creation of a Client file in the store 300 system.
 */
public class ClientFileFlows extends RouteBuilder {

    /**
     * This class create a process that will mock the fact we don't have a real input and put a fake client in the body.
     */
    private GetClientInDatabase getClientInDatabase;
    private AddClientToDataBase addClientToDataBase;
    private DeleteClientProcessor deleteClientProcessor;

    public ClientFileFlows() {
        this.addClientToDataBase = new AddClientToDataBase();
        this.getClientInDatabase = new GetClientInDatabase();
        this.deleteClientProcessor = new DeleteClientProcessor();
    }

    /**
     * This flow is the one for the root /clientFile in method post.
     * We check there if the client exists in the database via the property "client".
     * If he exists we send an error status of 409.
     * If not, we add him to the database and we send a success status of 200.
     *
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        from(Endpoint.CREATE_CLIENT_FILE.getInstruction())
            .log("Start client creation processing")
            .setProperty("clientFirstName", simple("${property.client.firstName}"))
                /**
                 * {@link GetClientInDatabase#process(Exchange)}
                 */
            .process(getClientInDatabase)
            .log("Check of the client in Database")
            .choice()
                .when(simple("${property.databaseClient} == null"))
                    .log("The client doesn't exist")
                    .log("Begin process to add the client in the database")
                /**
                 * {@link AddClientToDataBase#process(Exchange)}
                 */
                    .process(addClientToDataBase)
                    .setProperty("status", constant(200))
                    .log("Client added to the database")
                .otherwise()
                    .setProperty("status", constant(409))
                    .log("Client already exist")
                .end()
            .log("End of the process")
                /**
                 * {@link ClientServiceRoute#configure()}
                 */
            .to(Endpoint.SEND_STATUS.getInstruction());

        from(Endpoint.GET_CLIENT_FILE.getInstruction())
            .log("Start get client processing")
            .setProperty("clientFirstName", simple("${header.clientFirstName}"))
                /**
                 * {@link GetClientInDatabase#process(Exchange)}
                 */
            .process(getClientInDatabase)
                .log("Client in Database : ${property.databaseClient}")
            .choice()
                .when(simple("${property.databaseClient} == null"))
                    .setProperty("status", constant(404))
                .otherwise()
                    .setBody(simple("${property.databaseClient}"))
                    .setProperty("status", constant(200))
                .end()
                /**
                 * {@link ClientServiceRoute#configure()}
                 */
            .to(Endpoint.SEND_STATUS.getInstruction());

        from(Endpoint.DELETE_CLIENT_FILE.getInstruction())
            .log("Start delete client processing")
                .setProperty("clientFirstName", simple("${header.clientFirstName}"))
                /**
                 * {@link DeleteClientProcessor#process(Exchange)}
                 */
                .process(deleteClientProcessor)
                /**
                 * {@link ClientServiceRoute#configure()}
                 */
            .to(Endpoint.SEND_STATUS.getInstruction());

    }

    private class DeleteClientProcessor extends SuperProcessor {

        /**
         * This process permits to delete a client thanks to the user's first name.
         *
         * @param exchange This object contains the clientFirstName property.
         * @throws Exception
         */
        @Override
        public void process(Exchange exchange) throws Exception {
            // We get the clientFirstName property.
            String clientFirstName = (String)exchange.getProperty("clientFirstName");
            // If we succeeded to delete the client we send status 200
            if (ClientStorage.delete(clientFirstName)){
                exchange.setProperty("status",200);
            }else { // We send status 404 if we did not manage to delete the client.
                exchange.setProperty("status",404);
            }
        }
    }

}
