package fr.unice.polytech.soa1.shop3000.business;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Quentin on 10/19/2015.
 */
public class ClientStorage {

    private static HashMap<String, Client> clientStorage = new HashMap<String, Client>();

    public static void create(String firstName, String lastName) {
        clientStorage.put(firstName, new Client(firstName,lastName));
    }

    public static Client read(String firstName) {
        return clientStorage.get(firstName);
    }

    public static void delete(String firstName) {
        clientStorage.remove(firstName);
    }

    /**
     * This method allow to add a client to the database.
     *
     * @param client
     */
    public static void addClient(Client client) {
        clientStorage.put(client.getFirstName(),client);
    }

    public static Collection<Client> findAll() {
        return clientStorage.values();
    }

    static {
        ClientStorage.create("Quentin","Cornevin");
        ClientStorage.create("Marc","Karassev");
        ClientStorage.create("Laureen","Ginier");
        ClientStorage.create("Guillaume","Rahbari");
        ClientStorage.create("Je connais pas","bien l'orthographe de vos noms");

    }

}
