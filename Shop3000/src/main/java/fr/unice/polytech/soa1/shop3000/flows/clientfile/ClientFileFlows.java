package fr.unice.polytech.soa1.shop3000.flows.clientfile;

import fr.unice.polytech.soa1.shop3000.mock.ClientFileMock;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
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
    private CheckClientInDatabase checkClientInDatabase;
    private AddClientToDataBase addClientToDataBase;

    public ClientFileFlows() {
        this.clientFileMock = new ClientFileMock();
        this.addClientToDataBase = new AddClientToDataBase();
        this.checkClientInDatabase = new CheckClientInDatabase();
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
                .log("Start processing")
                .process(checkClientInDatabase)
                .log("Checked of the client in Database")
                .choice()
                    // If the client already exists (the property "client" is set to null)
                    .when(simple("${property.client} == null"))
                            .log("Client already exist")
                    // Otherwise the client doesn't exist and we add him in the database.
                    .otherwise()
                            .log("The client doesn't exist")
                            .log("Begin process to add the client in the database")
                            .process(addClientToDataBase)
                            .log("Client added to the database")
                .log("End of the process");

        from(Endpoint.GET_CLIENT_FILE.getInstruction())
                ;

        from(Endpoint.DELETE_CLIENT_FILE.getInstruction())
                ;


    }
}
