package com.nttdata.ecommerce.controller;

import com.nttdata.ecommerce.entities.Product;
import com.nttdata.ecommerce.dto.ProductDTO;

import com.nttdata.ecommerce.mapper.Mapper;
import com.nttdata.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    private Mapper productMapper;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getProducts() {
        return productService.findAllProducts().stream()
                .map(product -> productMapper.map(product, ProductDTO.class))
                .toList();

    }
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        return productMapper.mapToDTO(product, ProductDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }
    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductDTO productDto) {
        Product productRequest = productMapper.mapToEntity(productDto, Product.class);
        Product product = productService.updateProduct(productRequest, id);
        return productMapper.mapToDTO(product, ProductDTO.class);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestBody @Valid ProductDTO productDto) {
        Product postRequest = productMapper.mapToEntity(productDto, Product.class);
        Product product = productService.createProduct(postRequest);
        return productMapper.mapToDTO(product, ProductDTO.class);
    }


}
