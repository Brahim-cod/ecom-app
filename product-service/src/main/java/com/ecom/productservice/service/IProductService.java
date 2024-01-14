package com.ecom.productservice.service;

import com.ecom.productservice.entities.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Product createProduct(Product product);

    // Read operations
    List<Product> getAllProducts();

    List<Product> getProductsByCategory(Long id);

    Optional<Product> getProductById(Long id);

    // Update operation
    Product updateProduct(Long id, Product updatedProduct);

    // Delete operation
    void deleteProduct(Long id);
}
