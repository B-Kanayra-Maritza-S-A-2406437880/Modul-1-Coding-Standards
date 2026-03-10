package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);

        assertEquals("p-001", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentWithStatus() {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData,
                "SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithRejectedStatus() {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData,
                "REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);

        assertThrows(IllegalArgumentException.class,
                () -> payment.setStatus("MEOW"));
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class,
                () -> new Payment("p-001", "VOUCHER_CODE", paymentData, "INVALID"));
    }
}