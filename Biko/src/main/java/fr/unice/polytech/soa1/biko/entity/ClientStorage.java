package fr.unice.polytech.soa1.biko.entity;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * This class allow us to mock a database with client in it.
 */
public class ClientStorage {

    private static HashMap<Long, Client> clientStorage = new HashMap<>();

    public static void create(String name, long id) {
        clientStorage.put(id, new Client(id,name));
    }

    public static Client read(long id) {
        return clientStorage.get(id);
    }

    public static void delete(long id) {
        clientStorage.remove(id);
    }

    /**
     * This method return a client with the given name, null if the name is not linked to a client
     *
     * @param name of the client
     * @return client found in the list, null otherwise
     */
    public static Client getClientByName(String name) {
        for(Client client : clientStorage.values()) {
            if(client.getName().equals(name)) {
                return client;
            }
        }
        return null;
    }

    public static Client getClientById(long id) {
        return clientStorage.get(id);
    }

    /**
     * This method allow to add a client to the database.
     *
     * @param client
     */
    public static void addClient(Client client) {
        clientStorage.put(client.getId(),client);
    }

    public static Collection<Client> findAll() {
        return clientStorage.values();
    }

    static {
        ClientStorage.create("user1",123456789);
        ClientStorage.create("user2",133456789);
        ClientStorage.create("user3",143456789);
        ClientStorage.create("user4",153456789);
    }


}
