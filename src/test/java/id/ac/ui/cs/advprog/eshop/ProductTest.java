package id.ac.ui.cs.advprog.eshop;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Product product;

    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertNotNull(this.product.getId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang",
                this.product.getProductName());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(100,
                this.product.getProductQuantity());
    }
}
