// src/main/java/id/ac/ui/cs/advprog/eshop/model/VoucherCodePayment.java
package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class VoucherCodePayment extends Payment {
    public VoucherCodePayment(String id, String method,
                              Map<String, String> paymentData, Order order) {
        super(id, method, paymentData, order);
    }
}