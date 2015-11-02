package fr.unice.polytech.soa1.shop3000.flows.cart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Quentin on 11/1/2015.
 */
public class AddItem {

    public boolean addItemToCart(String clientFirstName, InputStream item) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(item));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("coucou");
            out.append(line);
        }
        reader.close();

        System.out.println(clientFirstName +" : " + out);
        return true;
    }
}
