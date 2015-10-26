package fr.unice.polytech.soa1.beerShop;

import fr.unice.polytech.soa1.beerShop.data.CartData;
import fr.unice.polytech.soa1.beerShop.data.OrderData;
import fr.unice.polytech.soa1.beerShop.model.Cart;
import fr.unice.polytech.soa1.beerShop.model.Order;
import org.json.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by tom on 06/10/15.
 */

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderService extends BaseService{

    @GET
    @Path("/all")
    public Response getAllOrders() {

        JSONArray result = new JSONArray();
        for(Map.Entry<Long, Order> entry: OrderData.getData().entrySet()) {
            result.put(entryToJson(entry));
        }
        return Response.ok().entity(result.toString(2)).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrder(@PathParam("orderId") Long orderId, @QueryParam("username") String username, @QueryParam("password") String password) {

        //Bad user and password association
        if (!authenticate(username, password) ){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Order wantedOrder =  OrderData.get(orderId);

        if(wantedOrder == null || !wantedOrder.getCart().getOwner().equals(username)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        JSONArray result = new JSONArray();
        result.put(convertToJson(wantedOrder));
        return Response.ok().entity(result.toString(2)).build();
    }
}
