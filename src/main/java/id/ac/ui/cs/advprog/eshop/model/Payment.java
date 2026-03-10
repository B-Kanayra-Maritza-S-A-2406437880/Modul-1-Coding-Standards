package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    private static final List<String> VALID_STATUSES =
            Arrays.asList("PENDING", "SUCCESS", "REJECTED");

    public Payment(String id, String method,
                   Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = "PENDING";
    }

    public Payment(String id, String method,
                   Map<String, String> paymentData, String status) {
        this(id, method, paymentData);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException(
                    "Invalid payment status: " + status);
        }
        this.status = status;
    }
}