package fr.unice.polytech.soa1.biko.presentation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * This interface allow the client to interact with his cart. Which means add product in his cart, getClient
 * information about his cart and validate his cart to take order.
 */
@Path("/user/{userId}/cart")
@Produces(MediaType.APPLICATION_JSON)
public interface CartService {

    /**
     * This method allows the user to validate his cart and have the id of his order.
     * Once he got the id of his order, the user will be able to communicate with the order interface.
     *
     * @return a Response with the an id corresponding to his order
     */
    @POST
    Response validateCart(@PathParam("userId")  long userId, String payment);

    /**
     * This method allows the user to have all the information he need about his cart. Which means
     * the price of his cart and all the information about the element in his cart.
     *
     * @return A Response with a JSONArray of the price of his cart and all the JSONObject corresponding
     * to the item in his cart.
     */
    @GET
    Response getCartInformation(@PathParam("userId")  long userId) throws IOException;


    /**
     * This method allows the user to add an item to his cart.
     * @param userId
     * @param item the name of the item to add
     * @return a Response containing whether the item has been successfully added to the cart or not
     */
    @POST
    @Path("/item")
    Response addElement(@PathParam("userId")  long userId, String item);

    @POST
    @Path("/customItem")
    Response addCustomElement(@PathParam("userId") long userId, String customItem);

    /*
    @PUT
    @Path("/{date}")
    Response setDate(@PathParam("date") String date);*/

    /**
     * This method allows the user to set the type of shipping for his items
     * @param shipping the type of the shipping
     * @return a Response if whether the operation was successful or not
     */
    @PUT
    @Path("/shipping")
    Response setShipping(@PathParam("userId")  long userId, String shipping);

    /**
     * This method allows the user to set the address for the shipping
     * @param address the address for the shipping
     * @return a Response if whether the operation was successful or not
     */
    @PUT
    @Path("/address")
    Response setAddress(@PathParam("userId") long userId, String address);

}
