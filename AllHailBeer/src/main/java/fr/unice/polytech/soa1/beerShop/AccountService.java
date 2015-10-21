package fr.unice.polytech.soa1.beerShop;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.beerShop.data.AccountData;
import fr.unice.polytech.soa1.beerShop.data.BeerData;
import fr.unice.polytech.soa1.beerShop.model.Account;
import fr.unice.polytech.soa1.beerShop.model.Beer;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

/**
 * Created by guillaume on 28/09/2015.
 */
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountService extends BaseService {

    @Path("/all")
    @GET
    public Response getAllAccount(@QueryParam("compte") String admin) {

        if (!AccountData.getData().containsKey(admin)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        ObjectMapper mapper = new ObjectMapper();

        //Map<String,Account> users = AccountData.getData();
        JSONArray result = new JSONArray();
        for(Map.Entry<String, Account> entry: AccountData.getData().entrySet()) {

            result.put(entryToJson(entry));
        }
        return Response.ok().entity(result.toString(2)).build();
    }

    @POST
    public Response createAccount(String account){
        ObjectMapper mapper = new ObjectMapper();

        try {
            Account account1 = mapper.readValue(account,Account.class);
            System.out.println("cc");
            if (AccountData.getData().containsKey(account1.getUsername())) {
                return Response.status(Response.Status.CONFLICT).build();
            }else{
                System.out.println("cc2");
                AccountData.add(account1);
                return  Response.ok().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }



    @Path("/{name}/{password}")
    @GET
    public Response getAccount(@PathParam("name") String name, @PathParam("password") String password) {

        JSONArray result = new JSONArray();
        for(Map.Entry<String, Account> entry: AccountData.getData().entrySet()) {
            if (entry.getValue().getUsername().equals(name) && entry.getValue().getPassword().equals(password)){
                result.put(entryToJson(entry));
                return Response.ok().entity(result.toString(2)).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAccount (@PathParam("id") String accountName, @QueryParam("username") String username){

        for(Map.Entry<String, Account> entry: AccountData.getData().entrySet()) {
            if (entry.getValue().getUsername().equals(username) && entry.getValue().getUsername().equals(accountName)){
                AccountData.delete(entry.getValue());
                return  Response.ok().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/")
    public Response updateAccount (String accountUpdated, @QueryParam("username") String username) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Account account = mapper.readValue(accountUpdated,Account.class);
            for(Map.Entry<String, Account> entry: AccountData.getData().entrySet()) {
                if (entry.getValue().getUsername().equals(username) && entry.getValue().getUsername().equals(account.getUsername())){
                    AccountData.update(account);
                    return  Response.ok().build();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }



}