package id.ac.ui.cs.advprog.eshop;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
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

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testCreateProductAutoGenerateId() {
        Product product = new Product();
        product.setProductName("Produk Without ID");
        product.setProductQuantity(10);

        Product savedProduct = productRepository.create(product);
        assertNotNull(savedProduct.getId());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getId(), savedProduct.getId());
        savedProduct = productIterator.next();
        assertEquals(product2.getId(), savedProduct.getId());
        assertFalse(productIterator.hasNext());
    }


    @Test
    void testFindProductByIdNotFound() {
        // Assumption : Empty repository (no product)
        UUID randomId = UUID.randomUUID();
        Product foundProduct = productRepository.findProductById(randomId);
        assertNull(foundProduct);
    }

    @Test
    void testFindProductByIdNotFoundInPopulatedList() {
        // Assumption: 1 product added to repository before
        Product product = new Product();
        productRepository.create(product);

        Product foundProduct = productRepository.findProductById(UUID.randomUUID());
        assertNull(foundProduct);
    }

    @Test
    void testUpdateExistingProductSuccess(){
        Product product1 = new Product();
        product1.setProductName("Shampoo Serasoft");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product mauDiEditProduct = new Product();
        mauDiEditProduct.setId(product1.getId());
        mauDiEditProduct.setProductName("Shampoo Lifebuoy");
        mauDiEditProduct.setProductQuantity(20);

        Product updatedProduct = productRepository.update(mauDiEditProduct);
        assertNotNull(updatedProduct);

        assertEquals(product1.getId(), updatedProduct.getId());
        assertEquals("Shampoo Lifebuoy", updatedProduct.getProductName());
        assertEquals(20, updatedProduct.getProductQuantity());

    }

    @Test
    void testUpdateExistingProductFailedIdDidntMatch(){
        Product product2 = new Product();
        product2.setProductName("Kecap Bangau");
        product2.setProductQuantity(10);
        productRepository.create(product2);

        Product editedProduct = new Product();
        editedProduct.setProductName("Kecap Angsa (harusnya failed)");
        editedProduct.setProductQuantity(20);

        Product updatedProduct = productRepository.update(editedProduct);
        assertNull(updatedProduct);

        // make sure the previous product named "Kecap Bangau" doesn't change
        Product existingProduct = productRepository.findProductById(product2.getId());
        assertEquals(product2.getId(), existingProduct.getId());
        assertEquals("Kecap Bangau", existingProduct.getProductName());
        assertEquals(10, existingProduct.getProductQuantity());

    }


    @Test
    void testDeleteExistingProductSuccess(){
        Product product3 = new Product();
        product3.setProductName("Deterjen Rinso");
        product3.setProductQuantity(20);
        productRepository.create(product3);

        assertNotNull(productRepository.findProductById(product3.getId()));
        productRepository.delete(product3.getId());

        assertNull(productRepository.findProductById(product3.getId()));

    }

    @Test
    void testDeleteExistingProductFailed_datadoesntchance(){
        List<UUID> productIdsBeforeDelete = new ArrayList<>();
        Iterator<Product> iteratorBeforeDelete = productRepository.findAll();
        while (iteratorBeforeDelete.hasNext()) {
            productIdsBeforeDelete.add(iteratorBeforeDelete.next().getId());
        }

        productRepository.delete(UUID.randomUUID());

        List<UUID> productIdsAfterDelete = new ArrayList<>();
        Iterator<Product> iteratorAfterDelete = productRepository.findAll();
        while (iteratorAfterDelete.hasNext()) {
            productIdsAfterDelete.add(iteratorAfterDelete.next().getId());
        }

        assertTrue(productIdsAfterDelete.containsAll(productIdsBeforeDelete));

    }

}



