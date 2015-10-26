package fr.unice.polytech.soa1.beerShop;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.beerShop.data.AccountData;
import fr.unice.polytech.soa1.beerShop.data.BeerData;
import fr.unice.polytech.soa1.beerShop.data.CartData;
import fr.unice.polytech.soa1.beerShop.data.OrderData;
import fr.unice.polytech.soa1.beerShop.model.Account;
import fr.unice.polytech.soa1.beerShop.model.Beer;
import fr.unice.polytech.soa1.beerShop.model.Cart;
import fr.unice.polytech.soa1.beerShop.model.Order;
import org.json.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

/**
 * Created by tom on 06/10/15.
 */

@Path("/cart")
@Produces(MediaType.APPLICATION_JSON)
public class CartService extends BaseService {


    @GET
    @Path("/all")
    public Response getAllCarts(@QueryParam("compte") String admin) {

        if (!AccountData.getData().containsKey(admin)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        JSONArray result = new JSONArray();
        for(Map.Entry<String, Cart> entry: CartData.getData().entrySet()) {
            result.put(entryToJson(entry));
        }
        return Response.ok().entity(result.toString(2)).build();
    }

    @GET
    public Response getCart(@QueryParam("username") String user) {

        JSONArray result = new JSONArray();

        for(Map.Entry<String, Cart> entry: CartData.getData().entrySet()) {
            if (user.equals("admin") || entry.getValue().getOwner().equals(user)){
                result.put(convertToJson(entry.getValue()));

            }
        }
        if(result.length()>0){
            return Response.ok().entity(result.toString(2)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createCart(String cart, @QueryParam("username") String user){
        ObjectMapper mapper = new ObjectMapper();

        try {
            Cart cart1 = mapper.readValue(cart,Cart.class);
            if (!CartData.getData().containsKey(user)){
                CartData.add(cart1);
                return  Response.ok().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/validation")
    public Response validateCart(@QueryParam("username") String user, @QueryParam("cb") String cb){
        ObjectMapper mapper = new ObjectMapper();

        for(Map.Entry<String, Cart> entry: CartData.getData().entrySet()) {
            if (entry.getValue().getOwner().equals(user)){
                Long orderId = OrderData.add(new Order(entry.getValue(),cb));
                CartData.delete(entry.getValue());
                return  Response.ok().entity(orderId).build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    public Response updateCart (String cart, @QueryParam("username") String user){
        ObjectMapper mapper = new ObjectMapper();

        try {
            Cart cart1 = mapper.readValue(cart,Cart.class);
            for(Map.Entry<String, Cart> entry: CartData.getData().entrySet()) {
                if (entry.getValue().getOwner().equals(user)){
                    CartData.update(cart1);
                    return  Response.ok().build();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    public Response deleteCart (@QueryParam("username") String user) {


        for(Map.Entry<String, Cart> entry: CartData.getData().entrySet()) {
            if (entry.getValue().getOwner().equals(user)){
                CartData.delete(entry.getValue());
                return  Response.ok().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


}
