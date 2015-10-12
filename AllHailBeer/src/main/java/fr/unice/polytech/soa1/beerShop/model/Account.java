package fr.unice.polytech.soa1.beerShop.model;

/**
 * Created by tom on 28/09/15.
 */
public class Account {
    private String username;
    private String password;

    public Account(){

    }

    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
