package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import java.util.Map;

public class VoucherCodePayment extends Payment {
    public VoucherCodePayment(String id, String method,
                              Map<String, String> paymentData, Order order) {
        super(id, method, paymentData, order);
        if (isValidVoucher(paymentData)) {
            this.setStatus(PaymentStatus.SUCCESS.getValue());
        } else {
            this.setStatus(PaymentStatus.REJECTED.getValue());
        }
    }

    private boolean isValidVoucher(Map<String, String> data) {
        String code = data.get("voucherCode");
        return isNotEmpty(code)
                && isSixteenChars(code)
                && isStartWithESHOP(code)
                && hasEightNumerics(code);
    }


    private boolean isNotEmpty(String code) {
        return code != null && !code.isEmpty();
    }

    private boolean isSixteenChars(String code) {
        return code.length() == 16;
    }

    private boolean isStartWithESHOP(String code) {
        return code.startsWith("ESHOP");
    }

    private boolean hasEightNumerics(String code) {
        long count = code.chars()
                .filter(Character::isDigit)
                .count();
        return count == 8;
    }
}