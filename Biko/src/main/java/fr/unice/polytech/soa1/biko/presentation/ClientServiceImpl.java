package fr.unice.polytech.soa1.biko.presentation;

import fr.unice.polytech.soa1.biko.entity.Client;
import fr.unice.polytech.soa1.biko.entity.ClientStorage;
import fr.unice.polytech.soa1.biko.entity.Order;
import fr.unice.polytech.soa1.biko.entity.OrderStorage;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * This interface allow the client to communicate with our client service. Which means add or remove
 * user from the database and getClient information about the users.
 */
public class ClientServiceImpl implements ClientService {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * This method allows the user to have the list of all the client registered in the system.
     *
     * @return a JSONArray with a JSONObject for each client in the system.
     */
    @Override
    public Response getAllClients() {
        Collection<Client> clients = ClientStorage.findAll();
        try {
            return Response.ok(mapper.writeValueAsString(clients)).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * This method allows the user to add a user in the database with a given name and a given id
     *
     * @param client : JsonObject representation of the client
     * @return a Response with the information if the user have been added or not in the database. The Response is a NOT_ACCEPTABLE status
     * if the JSONObject send is not correct.
     */
    @Override
    public Response add(String client) {
        try {
            Client client1 = mapper.readValue(client,Client.class);
            ClientStorage.addClient(client1);
            return Response.ok().build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    /**
     * This method allows a client to delete a user of the database with a given id.
     *
     * @param id of the user to delete of the database
     * @return a Response with the information if the user have been added or not.
     */
    @Override
    public Response deleteClient(long id) {
        ClientStorage.delete(id);
        return Response.ok().build();
    }

    /**
     * This method allows a client to have the information of the user with his name.
     *
     * @param name of the user to look up in the database.
     * @return a JSONObject with the information of the user.
     */
    @Override
    public Response getClientId(String name) {
        Client client = ClientStorage.getClientByName(name);
        if(client != null) {
            try {
                return Response.ok(mapper.writeValueAsString(client)).build();
            } catch (IOException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * This method returns the information of an order from a user.
     * @param orderId Id of the order
     * @return information of the order
     */
   @Override
    public Response getOrder(@PathParam("orderId") long orderId){
        Order order = OrderStorage.read(orderId);
       try {
           return Response.ok(mapper.writeValueAsString(order)).build();
       } catch (IOException e) {
           return Response.status(Response.Status.NOT_FOUND).build();
       }
   }
}
