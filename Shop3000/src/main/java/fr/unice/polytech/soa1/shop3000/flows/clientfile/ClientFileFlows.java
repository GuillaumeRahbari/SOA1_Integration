package fr.unice.polytech.soa1.shop3000.flows.clientfile;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.mock.ClientFileMock;
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
    private ClientFileMock clientFileMock;
    private GetClientInDatabase getClientInDatabase;
    private AddClientToDataBase addClientToDataBase;
    private DeleteClientProcessor deleteClientProcessor;

    public ClientFileFlows() {
        this.clientFileMock = new ClientFileMock();
        this.addClientToDataBase = new AddClientToDataBase();
        this.getClientInDatabase = new GetClientInDatabase();
        this.deleteClientProcessor = new DeleteClientProcessor();
    }

    /**
     * This flow is the one for the root /clientFile in method post.
     * We check there if the client exists in the database via the property "client".
     * If he exists we do nothing for now.
     * If not, we add him to the database.
     *
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        from(Endpoint.CREATE_CLIENT_FILE.getInstruction())
            .log("Start client creation processing")
            .setProperty("clientFirstName", simple("${property.client.firstName}"))
            .process(getClientInDatabase)
            .log("Check of the client in Database")
            .choice()
                .when(simple("${property.databaseClient} == null"))
                    .log("The client doesn't exist")
                    .log("Begin process to add the client in the database")
                    .process(addClientToDataBase)
                    .setProperty("status", constant(200))
                    .log("Client added to the database")
                .otherwise()
                    .setProperty("status", constant(409))
                    .log("Client already exist")
                .end()
            .log("End of the process")
            .to(Endpoint.SEND_STATUS.getInstruction());

        from(Endpoint.GET_CLIENT_FILE.getInstruction())
            .log("Start get client processing")
            .setProperty("clientFirstName", simple("${header.clientFirstName}"))
            .process(getClientInDatabase)
                .log("Client in Database : ${property.databaseClient}")
            .choice()
                .when(simple("${property.databaseClient} == null"))
                    .setProperty("status", constant(404))
                .otherwise()
                    .setBody(simple("${property.databaseClient}"))
                    .setProperty("status", constant(200))
                .end()
            .to(Endpoint.SEND_STATUS.getInstruction());

        from(Endpoint.DELETE_CLIENT_FILE.getInstruction())
            .log("Start delete client processing")
                .setProperty("clientFirstName", simple("${header.clientFirstName}"))
                .process(deleteClientProcessor)
            .to(Endpoint.SEND_STATUS.getInstruction());

    }

    private class DeleteClientProcessor extends SuperProcessor {
        @Override
        public void process(Exchange exchange) throws Exception {
            String clientFirstName = (String)exchange.getProperty("clientFirstName");
            if (ClientStorage.delete(clientFirstName)){
                exchange.setProperty("status",200);
            }else {
                exchange.setProperty("status",404);
            }
        }
    }
}
