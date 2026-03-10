// src/test/java/id/ac/ui/cs/advprog/eshop/model/BankTransferPaymentTest.java
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

class BankTransferPaymentTest {
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
    void testValidBankTransfer() {
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        BankTransferPayment payment = new BankTransferPayment("p-001",
                PaymentMethod.BANK_TRANSFER.getValue(), paymentData, order);

        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
    }

    @Test
    void testBankNameEmpty() {
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF123456");
        BankTransferPayment payment = new BankTransferPayment("p-001",
                PaymentMethod.BANK_TRANSFER.getValue(), paymentData, order);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testBankNameNull() {
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "REF123456");
        BankTransferPayment payment = new BankTransferPayment("p-001",
                PaymentMethod.BANK_TRANSFER.getValue(), paymentData, order);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testReferenceCodeEmpty() {
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");
        BankTransferPayment payment = new BankTransferPayment("p-001",
                PaymentMethod.BANK_TRANSFER.getValue(), paymentData, order);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testReferenceCodeNull() {
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", null);
        BankTransferPayment payment = new BankTransferPayment("p-001",
                PaymentMethod.BANK_TRANSFER.getValue(), paymentData, order);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}