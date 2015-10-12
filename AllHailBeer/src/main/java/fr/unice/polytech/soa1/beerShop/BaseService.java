package fr.unice.polytech.soa1.beerShop;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.beerShop.data.AccountData;
import fr.unice.polytech.soa1.beerShop.model.Account;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tom on 06/10/15.
 */
public class BaseService {

    protected  <T,U>  JSONObject entryToJson(Map.Entry<T,U> entry){
        ObjectMapper mapper = new ObjectMapper();
        JSONObject obj = new JSONObject();
        JSONObject value = new JSONObject();
        try {
            value = new JSONObject(mapper.writeValueAsString(entry.getValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        obj.put(entry.getKey().toString(),value);

        return obj;
    }

    protected JSONObject convertToJson(Object obj){
        ObjectMapper mapper = new ObjectMapper();

        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj = new JSONObject(mapper.writeValueAsString(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObj;
    }

    protected Boolean authenticate(String user, String password){
        for(Map.Entry<String, Account> entry : AccountData.getData().entrySet()){
            if (entry.getKey().equals(user) && entry.getValue().getPassword().equals(password))
                return true;
        }
        return false;
    }
}
