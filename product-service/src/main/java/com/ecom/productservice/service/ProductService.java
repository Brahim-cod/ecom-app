package com.ecom.productservice.service;

import com.ecom.productservice.entities.Product;
import com.ecom.productservice.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        // Add any business logic or validation before saving
        return productRepository.save(product);
    }

    // Read operations
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public List<Product> getProductsByCategory(Long id) {
        return productRepository.findAllByCategory_Id(id);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Update operation
    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    // Update fields based on your requirements
                    existingProduct.setTitle(updatedProduct.getTitle());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setQuantity(updatedProduct.getQuantity());

                    // Save and return the updated product
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
    }

    // Delete operation
    @Override
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Product with id " + id + " not found");
        }
    }
}
