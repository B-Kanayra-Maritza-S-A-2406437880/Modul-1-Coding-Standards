// src/main/java/id/ac/ui/cs/advprog/eshop/model/PaymentFactory.java
package id.ac.ui.cs.advprog.eshop.model;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class PaymentFactory {
    public Payment create(Order order, String method,
                          Map<String, String> paymentData) {
        return null;
    }
}