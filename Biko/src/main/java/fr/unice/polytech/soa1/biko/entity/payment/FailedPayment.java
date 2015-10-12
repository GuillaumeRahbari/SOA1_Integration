package fr.unice.polytech.soa1.biko.entity.payment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin on 10/5/2015.
 */
public class FailedPayment {

    private List<Payment> failedPayment;

    public FailedPayment(List<Payment> failedPayment) {
        this.failedPayment = failedPayment;
    }

    public FailedPayment() {
        this.failedPayment = new ArrayList<>();
    }

    public List<Payment> getFailedPayment() {
        return failedPayment;
    }

    public void setFailedPayment(List<Payment> failedPayment) {
        this.failedPayment = failedPayment;
    }
}
