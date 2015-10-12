package fr.unice.polytech.soa1.biko.presentation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * This interface allows the client to communicate with the command service. Which means he can getClient
 * information about the status of his command.
 * In addition this interface allow the delivery service to update the status of the command.
 */
@Path("/order/{orderId}")
@Produces(MediaType.APPLICATION_JSON)
public interface OrderService {

    /**
     * This method allows a user the retrieve the status of an order
     * @param orderId the id of the order
     * @return
     */
    @GET
    Response getOrder(@PathParam("orderId") long orderId);

    @GET
    @Path("/status")
    Response getCommandStatus(@PathParam("orderId") long orderId);

    /**
     * This method allows a user to retrieve the tracking number of an order
     * @param orderId the id of the order
     * @return
     */
    @GET
    @Path("/tracking")
    Response getTrackingNumber(@PathParam("orderId") long orderId);

    /**
     * This method updates the status of an order
     * @param orderId the id of the order
     * @param status the new status of the order
     * @return
     */
    @PUT
    @Path("/status")
    Response updateStatus(@PathParam("orderId") long orderId, String status);

}
