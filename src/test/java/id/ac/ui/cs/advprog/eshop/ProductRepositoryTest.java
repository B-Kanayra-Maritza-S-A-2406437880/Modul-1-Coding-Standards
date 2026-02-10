package id.ac.ui.cs.advprog.eshop;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {

    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(UUID.fromString("e8df9e46-90b1-437d-a0bf-d0821de9095"));
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    // Update feature tests
    @Test
    void testUpdateExistingProductSuccess(){
        UUID existingidTest1 = UUID.fromString("123e4567-e89b-12d3-a456-556642440000");
        Product product1 = new Product();
        product1.setProductId(existingidTest1);
        product1.setProductName("Shampoo Serasoft");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product mauDiEditProduct = new Product();

        // same id as the previous product
        mauDiEditProduct.setProductId(existingidTest1);
        mauDiEditProduct.setProductName("Shampoo Lifebuoy");
        mauDiEditProduct.setProductQuantity(20);

        Product updatedProduct = productRepository.update(mauDiEditProduct);
        assertNotNull(updatedProduct);

        assertEquals(existingidTest1, updatedProduct.getProductId());
        assertEquals("Shampoo Lifebuoy", updatedProduct.getProductName());
        assertEquals(20, updatedProduct.getProductQuantity());


    }

    @Test
    // ID didn't match
    void testUpdateExistingProductFailed(){
        UUID idTest2 = UUID.fromString("123e4567-e89b-12d3-a456-556642440001");
        Product product2 = new Product();
        product2.setProductId(idTest2);
        product2.setProductName("Kecap Bangau");
        product2.setProductQuantity(10);
        productRepository.create(product2);

        Product editedProduct = new Product();
        // try with different product ID
        editedProduct.setProductId(UUID.fromString("123e4567-e89b-12d3-a456-556642440010"));
        editedProduct.setProductName("Kecap Angsa (harusnya failed)");
        editedProduct.setProductQuantity(20);

        Product updatedProduct = productRepository.update(editedProduct);
        assertNull(updatedProduct);

        // make sure the previous product named "Kecap Bangau" doesn't change
        Product existingProduct = productRepository.findProductById(idTest2);
        assertEquals(idTest2, existingProduct.getProductId());
        assertEquals("Kecap Bangau", existingProduct.getProductName());
        assertEquals(10, existingProduct.getProductQuantity());

    }

    @Test
    void testDeleteExistingProductSuccess(){
        UUID idTest3 = UUID.fromString("123e4567-e89b-12d3-a456-556642440003");
        Product product3 = new Product();
        product3.setProductId(idTest3);
        product3.setProductName("Deterjen Rinso");
        product3.setProductQuantity(20);
        productRepository.create(product3);

        assertNotNull(productRepository.findProductById(idTest3));
        productRepository.delete(idTest3);

        assertNull(productRepository.findProductById(idTest3));

    }

    @Test
    void testDeleteExistingProductFailed_datadoesntchance(){
        UUID idTest4 = UUID.fromString("123e4567-e89b-12d3-a456-556642440050");
        Product product4 = new Product();
        product4.setProductId(idTest4);
        product4.setProductName("Bumbu Royco");
        product4.setProductQuantity(20);

        List<UUID> productIdsBeforeDelete = new ArrayList<>();
        Iterator<Product> iteratorBeforeDelete = productRepository.findAll();

        while(iteratorBeforeDelete.hasNext()){
            productIdsBeforeDelete.add(iteratorBeforeDelete.next().getProductId());
        }

        // Delete with an id that are not found
        UUID notFoundId = UUID.fromString("123e4567-e89b-12d3-a456-556642440999");
        productRepository.delete(notFoundId);

        List<UUID> productIdsAfterDelete = new ArrayList<>();
        Iterator<Product> iteratorAfterDelete = productRepository.findAll();
        while(iteratorAfterDelete.hasNext()){
            productIdsAfterDelete.add(iteratorAfterDelete.next().getProductId());
        }

        assertTrue(productIdsAfterDelete.containsAll(productIdsBeforeDelete));

    }

}
