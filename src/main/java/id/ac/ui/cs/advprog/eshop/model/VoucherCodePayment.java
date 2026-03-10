package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import java.util.Map;

public class VoucherCodePayment extends Payment {
    public VoucherCodePayment(String id, String method,
                              Map<String, String> paymentData, Order order) {
        super(id, method, paymentData, order);
        if (validateVoucher(paymentData)) {
            this.setStatus(PaymentStatus.SUCCESS.getValue());
        } else {
            this.setStatus(PaymentStatus.REJECTED.getValue());
        }
    }

    private boolean validateVoucher(Map<String, String> data) {
        String code = data.get("voucherCode");
        if (code == null || code.isEmpty()){
            return false;
        }
        if (code.length() != 16) {
            return false;
        }

        if (!code.startsWith("ESHOP")){
            return false;
        }

        long numericCount = code.chars()
                .filter(Character::isDigit)
                .count();
        return numericCount == 8;
    }
}