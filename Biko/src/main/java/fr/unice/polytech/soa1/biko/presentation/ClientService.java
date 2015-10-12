package fr.unice.polytech.soa1.biko.presentation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * This interface allow the client to communicate with our client service. Which means add or remove
 * user from the database and getClient information about the users.
 */
@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
public interface ClientService {

    /**
     * This method allow the user to have the list of all the client registered in the system.
     *
     * @return a JSONArray with a JSONObject for each client in the system.
     */
    @GET
    Response getAllClients();


    /**
     * This method allow the user to add a user in the database with a given name and a given id
     *
     * @param client : JsonObject reprensenting the client
     * @return a Response with the information if the user have been added or not in the database.
     */
    @POST
    Response add(String client);

    /**
     * This method allow a client to delete a user of the database with a given id.
     *
     * @param id of the user to delete of the database
     * @return a Response with the information if the user have been added or not.
     */
    @DELETE
    @Path("/id/{id}")
    Response deleteClient(@PathParam("id")  long id);

    /**
     * This method all a client to have the information of the user with his name.
     *
     * @param name of the user to look up in the database.
     * @return a JSONObject with the information of the user.
     */
    @GET
    @Path("/name/{name}")
    Response getClientId(@PathParam("name")String name);

    /**
     * This method returns the information of an order from a user.
     * @param orderId Id of the order
     * @return information of the order
     */
    @GET
    @Path("/order/{orderId}")
    Response getOrder(@PathParam("orderId") long orderId);

}
