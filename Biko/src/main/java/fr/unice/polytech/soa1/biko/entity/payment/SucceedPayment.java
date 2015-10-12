package fr.unice.polytech.soa1.biko.entity.payment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin on 10/5/2015.
 */
public class SucceedPayment {

    private List<Payment> succeedPayment;

    public SucceedPayment(List<Payment> succeedPayment) {
        this.succeedPayment = succeedPayment;
    }

    public SucceedPayment() {
        this.succeedPayment = new ArrayList<>();
    }

    public List<Payment> getSucceedPayment() {
        return succeedPayment;
    }

    public void setSucceedPayment(List<Payment> succeedPayment) {
        this.succeedPayment = succeedPayment;
    }
}
