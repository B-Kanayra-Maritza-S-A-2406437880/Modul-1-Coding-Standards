package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product){
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll(){
        return productData.iterator();
    }

    public Product findProductById(UUID id){
        for(Product product : productData){
            if(product.getProductId().equals(id)){
                return product;

            } 
        } return null;
    }

    
    public Product update(Product editedProduct){
        for(Product currentProduct : productData){
            if(currentProduct.getProductId().equals(editedProduct.getProductId())){
                currentProduct.setProductName(editedProduct.getProductName());
                currentProduct.setProductQuantity(editedProduct.getProductQuantity());
                return currentProduct;
            }
        } return null;

    }


    
}
