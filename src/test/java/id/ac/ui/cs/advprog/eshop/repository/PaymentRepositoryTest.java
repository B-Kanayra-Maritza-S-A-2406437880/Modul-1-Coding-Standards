package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        // Bikin order dulu
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);
        Order order = new Order("o-001", products, 1708560000L, "Safira Sudrajat");

        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> bankData = new HashMap<>();
        bankData.put("bankName", "BCA");
        bankData.put("referenceCode", "REF123456");

        payments = new ArrayList<>();
        Payment payment1 = new Payment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), voucherData, order);
        Payment payment2 = new Payment("p-002",
                PaymentMethod.BANK_TRANSFER.getValue(), bankData, order);

        payments.add(payment1);
        payments.add(payment2);
    }

    @Test
    void testSavePayment() {
        Payment payment = payments.get(0);
        Payment result = paymentRepository.save(payment);

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getStatus(), result.getStatus());
    }

    @Test
    void testSavePaymentUpdateExisting() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        Payment updatedPayment = new Payment(payment.getId(),
                payment.getMethod(), payment.getPaymentData(),
                payment.getOrder(),
                PaymentStatus.SUCCESS.getValue());
        Payment result = paymentRepository.save(updatedPayment);

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(1, paymentRepository.findAll().size());
    }

    @Test
    void testFindByIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment result = paymentRepository.findById(payments.get(0).getId());

        assertEquals(payments.get(0).getId(), result.getId());
        assertEquals(payments.get(0).getMethod(), result.getMethod());
        assertEquals(payments.get(0).getStatus(), result.getStatus());
    }

    @Test
    void testFindByIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment result = paymentRepository.findById("p-999");

        assertNull(result);
    }

    @Test
    void testFindAllEmpty() {
        List<Payment> result = paymentRepository.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllNotEmpty() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> result = paymentRepository.findAll();

        assertEquals(2, result.size());
    }
}