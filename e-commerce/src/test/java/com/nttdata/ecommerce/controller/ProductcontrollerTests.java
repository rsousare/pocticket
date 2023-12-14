package com.nttdata.ecommerce.controller;

import com.nttdata.ecommerce.ECommerceApplicationTests;
import com.nttdata.ecommerce.entities.Product;
import com.nttdata.ecommerce.exceptions.ResourceNotFoundException;
import com.nttdata.ecommerce.service.ProductServiceImpl;
import com.nttdata.ecommerce.repository.ProductRepository;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = ECommerceApplicationTests.class)
class ProductcontrollerTests {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productModel;
    private List<Product> mockProducts;

    @BeforeEach
    public void setup() {
        mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1L, "Product1", 99.99));
        mockProducts.add(new Product(2L, "Product2",99.99));
        mockProducts.add(new Product(3L, "Product3",99.99));
        mockProducts.add(new Product(4L, "Product4",99.99));
    }

    @AfterEach
    void teardown() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Testing findAllProducts() method in ProductService")
    void findAllProductsTest() {
      when(productRepository.findAll()).thenReturn(mockProducts);

      List<Product> result = productModel.findAllProducts();
      assertEquals(mockProducts, result);
      verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Testing getProductById() method in ProductService")
    void productByIdTest() {

        Optional<Product> optionalProduct = Optional.of(mockProducts.get(0));
        when(productRepository.findById(1L)).thenReturn(optionalProduct);

        Product result = productModel.getProductById(1L);
        assertEquals(mockProducts.get(0), result);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Testing getProductById() method in ProductService when ID not found")
    void getProductByIdNotFound() {
        when(productRepository.findById(11L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productModel.getProductById(111L));
    }

    @Test
    @DisplayName("Testing create() method in ProductService")
    void createProductTest() {
        Product newProduct = new Product(1L,"NewProduct", 99.99);
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        Product result = productModel.createProduct(newProduct);
        assertEquals(newProduct, result);
        verify(productRepository, times(1)).save(newProduct);
    }


    @Test
    @DisplayName("Testing update() method in ProductService when ID is not found")
    void productServiceUpdateIdNotFound() {

        when(productRepository.findById(11L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productModel.getProductById(11L));
    }

    @Test
    @DisplayName("Testing delete() method in ProductService")
    void deleteProductTest() {
        Optional<Product> optionalProduct = Optional.of(mockProducts.get(0));
        when(productRepository.findById(1L)).thenReturn(optionalProduct);
        assertDoesNotThrow(() -> productModel.deleteProduct(optionalProduct.get().getId()));
    }
    @Test
    @DisplayName("Testing delete() method in ProductService when ID not found")
    void deleteProductTestWhenIdNotFound() {
        int productId = 99;
        Optional<Product> optionalProduct = Optional.of(mockProducts.get(0));

        when(productRepository.findById(11L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productModel.getProductById(11L));
    }

    }



