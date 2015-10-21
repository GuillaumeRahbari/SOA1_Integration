package fr.unice.polytech.soa1.shop3000.business;

/**
 * @author Quentin Cornevin
 *
 * This class represent the client of the application
 */
public class Client {

    private String firstName;
    private String lastName;

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
