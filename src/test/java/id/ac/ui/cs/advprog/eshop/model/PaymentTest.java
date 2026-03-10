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

class PaymentTest {
    private Map<String, String> paymentData;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("o-001", products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Payment payment = new Payment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order);

        assertEquals("p-001", payment.getId());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(order, payment.getOrder());
    }

    @Test
    void testCreatePaymentWithStatus() {
        Payment payment = new Payment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order,
                PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentWithRejectedStatus() {
        Payment payment = new Payment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order,
                PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = new Payment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order);
        payment.setStatus(PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = new Payment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order);
        payment.setStatus(PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        Payment payment = new Payment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order);

        assertThrows(IllegalArgumentException.class,
                () -> payment.setStatus("MEOW"));
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class,
                () -> new Payment("p-001",
                        PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order,
                        "INVALID"));
    }
}