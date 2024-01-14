package com.ecom.productservice.web.controller;

import com.ecom.productservice.entities.Product;
import com.ecom.productservice.service.IProductService;
import com.ecom.productservice.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    private IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // Create operation
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // Read operations
    @GetMapping("/products")
    @CircuitBreaker(name = "productService", fallbackMethod = "getDefaultProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/products/category/{id}")
    @CircuitBreaker(name = "productService", fallbackMethod = "getDefaultProducts")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long id) {
        List<Product> products = productService.getProductsByCategory(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    @CircuitBreaker(name = "productService", fallbackMethod = "getDefaultProduct")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update operation
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product updated = productService.updateProduct(id, updatedProduct);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Delete operation
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public Product getDefaultProduct(Long id,Exception exception){
        return Product.builder()
                .id(id)
                .title("Default")
                .price(100)
                .quantity(10)
                .build();
    }

    public List<Product> getDefaultProducts(Exception exception){
        return List.of();
    }
}
