package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import java.util.Map;

public class BankTransferPayment extends Payment {
    public BankTransferPayment(String id, String method,
                               Map<String, String> paymentData, Order order) {
        super(id, method, paymentData, order);
        if (isValidBankTransfer(paymentData)) {
            this.setStatus(PaymentStatus.PENDING.getValue());
        } else {
            this.setStatus(PaymentStatus.REJECTED.getValue());
        }
    }

    private boolean isValidBankTransfer(Map<String, String> data) {
        return isNotEmpty(data.get("bankName"))
                && isNotEmpty(data.get("referenceCode"));
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}