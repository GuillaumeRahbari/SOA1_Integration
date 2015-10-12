package fr.unice.polytech.soa1.biko.presentation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * This interface allow the user to interact with the catalog of the eCommerce.
 */
@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
public interface CatalogService{

    /**
     * This method allow the user to get all the element of the catalog
     *
     * @return a JSONArray with all the JSONObject representing the element of the catalog
     * @throws IOException
     */
    @GET
    Response getCatalog() throws IOException;

    /**
     * This method allow the user to get all the information of a bike with his id.
     *
     * @param id of the bike
     * @return a JSONObject with all the information of the bike
     * @throws IOException
     */
    @GET
    @Path("/{id}")
    Response getBike(@PathParam("id") long id) throws IOException;

    /**
     * This method allow the user to add a bike to the database if he put a JSONObject with a good form.
     * Example :
     * {
     *     "name": "givenName"
     *     "color": "givenColor"
     *     "id" : "givenID"
     * }
     *
     * The name and the color are string and the id is a long.
     *
     * @param bike : JSONObject representing the bike
     * @return a response telling if the bike have been add or not. The Response is a NOT_ACCEPTABLE status
     * if the JSONObject send is not correct.
     */
    @POST
    Response addBike(String bike);

    /**
     * This method allow the user to delete a bike with his id.
     *
     * @param id of the bike we want to delete
     * @return a Response telling if the bike have been delete, or if the resource have not been found otherwise.
     */
    @DELETE
    @Path("/{id}")
    Response deleteBike(@PathParam("id") long id);

    /**
     * This method allows the user to get all the components associated
     * to the type.
     * @param type the type of the elements to fetch
     * @return a Responses with the elements associated to the type
     */
    @GET
    @Path("/component/types/{type}")
    Response getComponentsFromType (@PathParam("type") String type);

    /**
     * This method allows the user to retrieve all of the components used
     * to customize a bike
     * @return a Response with all the types
     */
    @GET
    @Path("/component/types")
    Response getAllType();

    /**
     * This method allows a user to add a new component
     * @param name the name of the component
     * @param price the price of the component
     * @param type the type of the component
     * @return a Response if whether the operation was successful or not
     */
    @PUT
    @Path("/component")
    Response addComponent(String component);


    /**
     * This method allows a user to delete a component
     * @param name the name of the component to delete
     * @return a Response if whether the operation was successful or not
     */
    @DELETE
    @Path("/component/name/{name}")
    Response deleteComponent(@PathParam("name") String name);
}
