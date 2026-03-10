package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("PENDING"),
    SUCCESS("SUCCESS"),
    REJECTED("REJECTED");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String status) {
        for (PaymentStatus ps : PaymentStatus.values()) {
            if (ps.getValue().equals(status)) {
                return true;
            }
        }
        return false;
    }
}