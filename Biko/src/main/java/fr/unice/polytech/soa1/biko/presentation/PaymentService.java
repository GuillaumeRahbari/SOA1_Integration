package fr.unice.polytech.soa1.biko.presentation;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Nabil on 05/10/2015.
 */
@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
public interface PaymentService {

    /**
     * This method allows a user to retrieve all the payments
     * @return a Response with all the payments
     */
    @GET
    Response getPayments();

    /**
     * This method allows a user to add a new payment. The JSON must have the
     * same attributes as the Payment class.
     * @param payment the payment to add
     * @return
     */
    @Path("/failed")
    @PUT
    Response updateFailedPayment(String payment);


    /**
     * This method allows a user to retrieve all the failed payments
     * @return a Response with all the failed payments
     */
    @Path("/failed")
    @GET
    Response getPaymentsFailed();


    @Path("/succeeded")
    @PUT
    Response updateSucceedPayment(String payment);

    /**
     * This method allows a user to retrieve all the payments that have succeeded
     * @return a Response with all the payments that have succeeded
     */
    @Path("/succeeded")
    @GET
    Response getPaymentsSucceeded();
/*
    @Path("/succeeded")
    @PUT
    Response addPaymentsSucceeded(String payments);

    @Path("/failed")
    @PUT
    Response addPaymentsFailed(String payments);

    @Path("/toDo")
    @GET
    Response addPaymentsToDo(String payments);
    */

}
