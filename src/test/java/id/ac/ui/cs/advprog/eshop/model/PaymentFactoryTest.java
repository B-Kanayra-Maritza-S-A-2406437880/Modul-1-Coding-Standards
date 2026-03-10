package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentFactoryTest {
    private PaymentFactory paymentFactory;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentFactory = new PaymentFactory();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("o-001", products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreateVoucherCodePayment() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = paymentFactory.create(order,
                PaymentMethod.VOUCHER_CODE.getValue(), data);

        assertInstanceOf(VoucherCodePayment.class, payment);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreateVoucherCodePaymentInvalid() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "INVALID");

        Payment payment = paymentFactory.create(order,
                PaymentMethod.VOUCHER_CODE.getValue(), data);

        assertInstanceOf(VoucherCodePayment.class, payment);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreateBankTransferPayment() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF123456");

        Payment payment = paymentFactory.create(order,
                PaymentMethod.BANK_TRANSFER.getValue(), data);

        assertInstanceOf(BankTransferPayment.class, payment);
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    }

    @Test
    void testCreateBankTransferPaymentInvalid() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "");
        data.put("referenceCode", "REF123456");

        Payment payment = paymentFactory.create(order,
                PaymentMethod.BANK_TRANSFER.getValue(), data);

        assertInstanceOf(BankTransferPayment.class, payment);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}