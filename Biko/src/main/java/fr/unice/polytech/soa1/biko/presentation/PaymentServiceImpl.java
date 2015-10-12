package fr.unice.polytech.soa1.biko.presentation;

import fr.unice.polytech.soa1.biko.entity.payment.*;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 05/10/2015.
 */
public class PaymentServiceImpl implements PaymentService {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * This method allows a user to retrieve all the payments
     * @return a Response with all the payments
     */
    @Override
    public Response getPayments() {
        try {
            return Response.ok(mapper.writeValueAsString(PaymentStorage.getPayments())).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    /**
     * This method allows a user to add a new payment. The JSON must have the
     * same attributes as the Payment class :
     * {
     *     "orderId": number
     *     "paymentStatus": ?
     *     "code": number
     *     "cv": number
     *     "date": number
     * }
     * @param failedPayment the payment to add
     * @return
     */
    public Response updateFailedPayment(String failedPayment){
        try {
            FailedPayment p = mapper.readValue(failedPayment, FailedPayment.class);
            PaymentStorage.updateFailedPayment(p);
            return Response.ok().build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

    }

    @Override
    public Response updateSucceedPayment(String succeedPayment) {
        try {
            SucceedPayment p = mapper.readValue(succeedPayment, SucceedPayment.class);
            PaymentStorage.updateSucceedPayment(p);
            return Response.ok().build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }


    /**
     * This method allows a user to retrieve all the failed payments
     * @return a Response with all the failed payments
     */
    @Override
    public Response getPaymentsFailed() {
        ArrayList<Payment> payments = PaymentStorage.getPayments();
        ArrayList<Payment> pl = new ArrayList<>();
        for(Payment p : payments){
            if(p.getPaymentStatus() == PaymentStatus.FAILED)
                pl.add(p);
        }
        try {
            return Response.ok(mapper.writeValueAsString(pl)).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    /**
     * This method allows a user to retrieve all the payments that have succeeded
     * @return a Response with all the payments that have succeeded
     */
    @Override
    public Response getPaymentsSucceeded() {
        ArrayList<Payment> payments = PaymentStorage.getPayments();
        ArrayList<Payment> pl = new ArrayList<>();
        for(Payment p : payments){
            if(p.getPaymentStatus() == PaymentStatus.SUCCEEDED)
                pl.add(p);
        }
        try {
            return Response.ok(mapper.writeValueAsString(pl)).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

/**
    @Override
    public Response addPaymentsFailed(List<Payment> payments) {
        for(Payment p : payments){
            PaymentStorage.getPayments().stream().filter(p2 -> p.getOrderId() == p2.getOrderId()).forEach(p2 -> p2.setPaymentStatus(PaymentStatus.FAILED));
        }
        for(Payment p : payments){
            OrderStorage.read(p.getOrderId()).setStatus(OrderStatus.AWAITING_PAYMENT);
        }
        return Response.ok().build();
    }
**/

    /**
    @Override
    public Response addPaymentsSucceeded(List<Payment> payments) {
       for(Payment p : payments){
           PaymentStorage.getPayments().stream().filter(p2 -> p.getOrderId() == p2.getOrderId()).forEach(p2 -> p2.setPaymentStatus(PaymentStatus.SUCCEEDED));
       }
        for(Payment p : payments){
            OrderStorage.read(p.getOrderId()).setStatus(OrderStatus.IN_PREPARATION);
        }
        return Response.ok().build();
    }
**/
}
