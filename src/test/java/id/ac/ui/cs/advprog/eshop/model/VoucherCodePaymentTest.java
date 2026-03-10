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

class VoucherCodePaymentTest {
    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("o-001", products, 1708560000L, "Safira Sudrajat");
        paymentData = new HashMap<>();
    }

    @Test
    void testValidVoucherCode() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        VoucherCodePayment payment = new VoucherCodePayment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order);

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeNot16Chars() {
        paymentData.put("voucherCode", "ESHOP123ABC");
        VoucherCodePayment payment = new VoucherCodePayment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeNotStartWithESHOP() {
        paymentData.put("voucherCode", "TOKOP1234ABC5678");
        VoucherCodePayment payment = new VoucherCodePayment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeNot8Numerics() {
        paymentData.put("voucherCode", "ESHOPABCDEFGHIJK");
        VoucherCodePayment payment = new VoucherCodePayment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeNull() {
        paymentData.put("voucherCode", null);
        VoucherCodePayment payment = new VoucherCodePayment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeEmpty() {
        paymentData.put("voucherCode", "");
        VoucherCodePayment payment = new VoucherCodePayment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeMore8Numerics() {
        paymentData.put("voucherCode", "ESHOP12345678901"); // 9 numerics
        VoucherCodePayment payment = new VoucherCodePayment("p-001",
                PaymentMethod.VOUCHER_CODE.getValue(), paymentData, order);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}