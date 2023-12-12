package com.nttdata.ecommerce.service;

import com.nttdata.ecommerce.entities.Product;

import java.util.List;


public interface ProductService {
    List<Product> findAllProducts();
    Product getProductById(Long id);
    void deleteProduct(Long id);
    Product updateProduct(Product product, Long id);
    Product createProduct(Product product);

}
