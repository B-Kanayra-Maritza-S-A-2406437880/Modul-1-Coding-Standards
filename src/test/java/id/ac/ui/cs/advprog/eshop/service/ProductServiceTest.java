package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreate() {
        when(productRepository.create(any(Product.class))).thenReturn(product);
        Product result = productService.create(product);

        assertNotNull(result);
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productService.findAll();
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductById() {
        UUID id = product.getProductId();
        when(productRepository.findProductById(id)).thenReturn(product);

        Product result = productService.findProductById(id);

        assertEquals(product, result);
        verify(productRepository, times(1)).findProductById(id);
    }

    @Test
    void testUpdate() {
        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);

        assertNotNull(result);
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void testDelete() {
        UUID id = product.getProductId();
        productService.delete(id);
        verify(productRepository, times(1)).delete(id);
    }
}