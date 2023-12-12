package com.nttdata.ecommerce.service;


import com.nttdata.ecommerce.entities.Product;
import com.nttdata.ecommerce.exceptions.ResourceNotFoundException;
import com.nttdata.ecommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {


    private ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + id + " not found."));
    }


    @Override
    public void deleteProduct(Long id) {

        Product existingProduct = productRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));
        productRepository.delete(existingProduct);
    }


    @Override
    public Product updateProduct(Product product, Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));

        if (product.getPrice() != null) {
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getName() != null && !product.getName().isBlank()) {
            existingProduct.setName(product.getName());
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }


}