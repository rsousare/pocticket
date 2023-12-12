package com.nttdata.ecommerce.service;

import com.nttdata.ecommerce.entities.Product;
import com.nttdata.ecommerce.exceptions.ResourceNotFoundException;
import com.nttdata.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    private List<Product> mockProducts;

    @AfterEach
    void teardown() {
        productRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1L, "Product1", 99.99));
        mockProducts.add(new Product(2L, "Product2", 99.99));
        mockProducts.add(new Product(3L, "Product3", 99.99));
        mockProducts.add(new Product(4L, "Product4", 99.99));
    }

    @Test
    @DisplayName("Testing findAllProducts() method in ProductService")
    void testFindAllProducts() {
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productService.findAllProducts();

        assertEquals(mockProducts, result);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Testing getProductById() method in ProductService")
    void testGetProductById() {

        long productId = 1L;
        Optional<Product> optionalProduct = Optional.of(mockProducts.get(0));

        when(productRepository.findById(productId)).thenReturn(optionalProduct);

        Product result = productService.getProductById(productId);

        assertEquals(mockProducts.get(0), result);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    @DisplayName("Testing getProductById() method in ProductService when ID not found")
    void testGetProductByIdNotFound() {

        long nonExistentProductId = 111L;
        when(productRepository.findById(nonExistentProductId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(nonExistentProductId));
    }

    @Test
    @DisplayName("Testing createProduct() method in ProductService")
    void testCreateProduct() {
        Product newProduct = new Product(5L, "NewProduct", 120.0);

        when(productRepository.save(newProduct)).thenReturn(newProduct);
        Product result = productService.createProduct(newProduct);

        assertEquals(newProduct, result);
        verify(productRepository, times(1)).save(newProduct);
    }

    @Test
    @DisplayName("Testing updateProduct() method in ProductService")
    void testUpdateProduct() {
        long productId = 1L;
        Product existingProduct = new Product(productId, "ExistingProduct", 50.0);
        Product updatedProduct = new Product(productId, "UpdatedProduct", 75.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(updatedProduct, productId);
        assertEquals(updatedProduct, result);
    }

    @Test
    @DisplayName("Testing updateProduct() method in ProductService when ID not found")
    void testUpdateProductWhenIdNotFound() {
        long nonExistentProductId = 111L;
        Product updatedProduct = new Product(nonExistentProductId, "UpdatedProduct", 75.0);

        when(productRepository.findById(nonExistentProductId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productService.updateProduct(updatedProduct, nonExistentProductId));
    }

    @Test
    @DisplayName("Testing deleteProduct() method in ProductService")
    void testDeleteProduct() {

        long productId = 1L;
        Optional<Product> optionalProduct = Optional.of(mockProducts.get(0));

        when(productRepository.findById(productId)).thenReturn(optionalProduct);

        assertDoesNotThrow(() -> productService.deleteProduct(productId));
        verify(productRepository, times(1)).delete(optionalProduct.get());
    }

    @Test
    @DisplayName("Testing deleteProduct() method in ProductService when ID not found")
    void testDeleteProductWhenIdNotFound() {
        long nonExistentProductId = 111L;
        when(productRepository.findById(nonExistentProductId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> productService.deleteProduct(nonExistentProductId));
    }
}
