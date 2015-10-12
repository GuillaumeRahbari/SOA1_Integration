package fr.unice.polytech.soa1.biko.presentation;

import fr.unice.polytech.soa1.biko.entity.ConnectedBike;
import fr.unice.polytech.soa1.biko.entity.ConnectedBikeStorage;
import fr.unice.polytech.soa1.biko.entity.stuff.Stuff;
import fr.unice.polytech.soa1.biko.entity.stuff.StuffStorage;
import fr.unice.polytech.soa1.biko.entity.stuff.Type;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * This interface allow the user to interact with the catalog of the eCommerce.
 */
public class CatalogServiceImpl implements CatalogService {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * This method allow the user to get all the element of the catalog
     *
     * @return a JSONArray with all the JSONObject representing the element of the catalog
     */
    @Override
    public Response getCatalog(){
        Collection<ConnectedBike> bikes =  ConnectedBikeStorage.findAll();
        try {
            return Response.ok().entity(mapper.writeValueAsString(bikes)).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * This method allow the user to get all the information of a bike with his id.
     *
     * @param id of the bike
     * @return a JSONObject with all the information of the bike
     */
    @Override
    public Response getBike(long id) {
        ConnectedBike connectedBike = ConnectedBikeStorage.read(id);
        try {
            return Response.ok(mapper.writeValueAsString(connectedBike)).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

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
    @Override
    public Response addBike(String bike) {
        ConnectedBike connectedBike = null;
        try {
            connectedBike = mapper.readValue(bike, ConnectedBike.class);
            ConnectedBikeStorage.add(connectedBike);
            return Response.ok().build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    /**
     * This method allow the user to delete a bike with his id.
     *
     * @param id of the bike we want to delete
     * @return a Response telling if the bike have been delete, or if the resource have not been found otherwise.
     */
    @Override
    public Response deleteBike(long id) {
        ConnectedBike connectedBike = ConnectedBikeStorage.read(id);
        if(connectedBike == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        ConnectedBikeStorage.delete(connectedBike.getName());
        return Response.ok().build();
    }

    /**
     * This method allows the user to get all the components associated
     * to the type.
     * @param type the type of the elements to fetch
     * @return a Responses with the elements associated to the type
     */
    @Override
    public Response getComponentsFromType(String type) {
        Type t = Type.forValue(type);
        try{
            return Response.ok(mapper.writeValueAsString(StuffStorage.findFromType(t))).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * This method allows the user to retrieve all of the components used
     * to customize a bike
     * @return a Response with all the types
     */
    @Override
    public Response getAllType() {
        try{
            return Response.ok(mapper.writeValueAsString(StuffStorage.getTypes())).build();
        } catch (IOException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * This method allows a user to add a new component
     *
     * @return a Response if whether the operation was successful or not
     */
    @Override
    public Response addComponent(String component) {
        try {
            Stuff stuff = mapper.readValue(component, Stuff.class);
            StuffStorage.addStuff(stuff);
            return Response.ok().build();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * This method allows a user to delete a component
     * @param name the name of the component to delete
     * @return a Response if whether the operation was successful or not
     */
    @Override
    public Response deleteComponent(String name) {
        StuffStorage.delete(name);
        return Response.ok().build();
    }
}
