package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class BankTransferPayment extends Payment {
    public BankTransferPayment(String id, String method,
                               Map<String, String> paymentData, Order order) {
        super(id, method, paymentData, order);
    }
}