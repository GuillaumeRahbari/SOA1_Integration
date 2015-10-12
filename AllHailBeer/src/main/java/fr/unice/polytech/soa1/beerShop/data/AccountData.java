package fr.unice.polytech.soa1.beerShop.data;

import fr.unice.polytech.soa1.beerShop.model.Account;
import fr.unice.polytech.soa1.beerShop.model.Beer;

import java.util.Map;

/**
 * Created by tom on 28/09/15.
 */
public class AccountData {
    private static Map<String, Account> data = DaoUtils.readData("accountData.json", String.class, Account.class);

    public static Account get(String accountId){
        return data.get(accountId);
    }

    public static void add(Account account){
        data.put(account.getUsername(),account);
    }

    public static void update(Account account){
        delete(account);
        data.put(account.getUsername(), account);
    }

    public static void delete(Account account){
        data.remove(account.getUsername());
    }

    public static Map<String, Account> getData() {
        return data;
    }

}
