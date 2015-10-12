package fr.unice.polytech.soa1.biko.presentation;

import fr.unice.polytech.soa1.biko.entity.*;
import fr.unice.polytech.soa1.biko.entity.orderSpecification.Address;
import fr.unice.polytech.soa1.biko.entity.orderSpecification.ShippingWrapper;
import fr.unice.polytech.soa1.biko.entity.payment.Payment;
import fr.unice.polytech.soa1.biko.entity.payment.PaymentInformation;
import fr.unice.polytech.soa1.biko.entity.payment.PaymentStorage;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * This interface allows the users to communicate with the cart services such
 * as adding items to ther cart, validating the cart, putting informations such
 * as the shipping address or type.
 */
public class CartServiceImpl implements CartService {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * This method allow the user to validate his cart and have the id of his order.
     * Once he got the id of his order, the user will be able to communicate with the order interface.
     * JSON example :
     *  {"securityCode":10,"creditCardNumber":10,"expirationDate":10}
     *
     * @return a Response with the an id corresponding to his order
     */
    @Override
    public Response validateCart(long userId, String client) {
        Cart cart = ClientStorage.read(userId).getCart();
        Order order = new Order(cart.getItems(),cart.getAddress(), cart.getShipping());
        OrderStorage.add(order);
        try {
            PaymentInformation paymentInformation = mapper.readValue(client,PaymentInformation.class);
            PaymentStorage.add(new Payment(paymentInformation, order.getId()));
            return Response.ok(mapper.writeValueAsString(order)).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    /**
     * This method allow the user to have all the information he need about his cart. Which means
     * the price of his cart and all the information about the element in his cart.
     *
     * @return A Response with a JSONArray of the price of his cart and all the JSONObject corresponding
     * to the item in his cart.
     */
    @Override
    public Response getCartInformation(long userId) throws IOException {
        // get the cart information from the userId
        Cart cart = ClientStorage.read(userId).getCart();
        return Response.ok(mapper.writeValueAsString(cart)).build();
    }


    /**
     * This method allows the user to add an element/item to the cart
     * @param userId the id of the user
     * @param item the name of the item to add
     * @return a Response if whether the operation was successful or not
     */
    @Override
    public Response addElement(long userId, String item) {
        try {
            ConnectedBike connectedBike = mapper.readValue(item, ConnectedBike.class);
            Client client = ClientStorage.getClientById(userId);
            if(client != null) {
                client.getCart().addItem(connectedBike);
                return Response.ok().build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    /**
     *
     * {"name":"bike2","id":133456789,"color":"blue","price":100,"customItem":[{
     "name": "Maxi-Handlebar",
     "type": "handlebar",
     "price": 100
     }]}
     *
     * @param userId
     * @param customItem
     * @return
     */
    @Override
    public Response addCustomElement(long userId, String customItem) {
        try {
            CustomBike customBike = mapper.readValue(customItem, CustomBike.class);
            Client client = ClientStorage.read(userId);
            if(client != null) {
                client.getCart().addItem(customBike);
                return Response.ok().build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    /**
     * This method allows the user to set the type of shipping for his items
     * @param shipping the type of the shipping
     * @return a Response if whether the operation was successful or not
     */
    @Override
    public Response setShipping(long userId, String shipping) {
        Client client = ClientStorage.getClientById(userId);
        if(client != null) {
            try {
                ShippingWrapper shipping1 = mapper.readValue(shipping, ShippingWrapper.class);
                client.getCart().setShipping(shipping1);
                return Response.ok().build();
            } catch (IOException e) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * This method allows the user to set the address for the shipping
     * @param address the address for the shipping
     * @return a Response if whether the operation was successful or not
     */
    @Override
    public Response setAddress(long userId, String address) {
        Address address1 = null;

        try {
            address1 = mapper.readValue(address, Address.class);
            if(ClientStorage.read(userId) != null) {
                ClientStorage.read(userId).getCart().setAddress(address1);
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
