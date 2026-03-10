package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.UUID;

@Component
public class PaymentFactory {
    public Payment create(Order order, String method,
                          Map<String, String> paymentData) {
        String id = UUID.randomUUID().toString();

        if (method.equals(PaymentMethod.VOUCHER_CODE.getValue())) {
            return new VoucherCodePayment(id, method, paymentData, order);
        }
        if (method.equals(PaymentMethod.BANK_TRANSFER.getValue())) {
            return new BankTransferPayment(id, method, paymentData, order);
        }

        return new Payment(id, method, paymentData, order);
    }
}