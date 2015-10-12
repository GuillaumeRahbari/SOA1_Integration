package fr.unice.polytech.soa1.biko.presentation;

import fr.unice.polytech.soa1.biko.entity.Order;
import fr.unice.polytech.soa1.biko.entity.OrderStorage;
import fr.unice.polytech.soa1.biko.entity.orderSpecification.OrderStatusWrapper;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * This interface allows users to communicate with the order service to
 * see or update the order status and the tracking number .
 */
public class OrderServiceImpl implements OrderService {

    private Order order;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Response getOrder(long orderID) {
        try {
            return Response.ok(mapper.writeValueAsString(OrderStorage.read(orderID))).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * This method allows a user the retrieve the status of an order
     * @param orderId the id of the order
     * @return
     */
    @Override
    public Response getCommandStatus(long orderId) {
        try {
            return Response.ok((mapper.writeValueAsString(OrderStorage.read(orderId).getStatus()))).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * This method allows a user to retrieve the tracking number of an order
     * @param orderId the id of the order
     * @return
     */
    @Override
    public Response getTrackingNumber(long orderId) {
        try {
            return Response.ok((mapper.writeValueAsString(OrderStorage.read(orderId).getTracking()))).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * This method updates the status of an order
     * @param orderId the id of the order
     * @param status the new status of the order
     * @return
     */
    @Override
    public Response updateStatus(long orderId, String status) {
        order = OrderStorage.read(orderId);
        if(order != null) {
            try {
                OrderStatusWrapper orderStatusWrapper = mapper.readValue(status,OrderStatusWrapper.class);
                order.setStatus(orderStatusWrapper);
                return Response.ok().build();
            } catch (IOException e) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
