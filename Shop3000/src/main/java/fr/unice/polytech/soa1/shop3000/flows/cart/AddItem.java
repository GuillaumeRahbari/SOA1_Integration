package fr.unice.polytech.soa1.shop3000.flows.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.flows.clientfile.CheckClientInDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin on 11/1/2015.
 */
public class AddItem {

    public boolean addItemToCart(String clientFirstName, InputStream item) throws IOException {
        Client client = ClientStorage.read(clientFirstName);
        // If the client exists.
        if (client != null){
            // We map the object to be a json object.
            ObjectMapper mapper = new ObjectMapper();
            CatalogItem catalogItem = mapper.readValue(bodyToString(item), CatalogItem.class);
            addItemToCart(client,catalogItem);
            return true;
        };
        return false;
    }

    /**
     * This method converts the body into a string.
     *
     * @param item The body request
     * @return A string that represents the request body.
     * @throws IOException
     */
    private String bodyToString (InputStream item) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(item));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        reader.close();
        return out.toString();
    }

    private void addItemToCart (Client client, CatalogItem catalogItem) {
        String shopName = catalogItem.getName();
        if (client.getCart().get(shopName) == null) {
            client.getCart().put(shopName, new ArrayList<CatalogItem>());
        }
        client.getCart().get(shopName).add(catalogItem);
    }
}
