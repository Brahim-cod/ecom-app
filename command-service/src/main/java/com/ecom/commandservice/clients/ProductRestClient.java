package com.ecom.commandservice.clients;

import com.ecom.commandservice.model.Product;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductRestClient {
    @GetMapping("/products/{id}")
    @CircuitBreaker(name = "productService", fallbackMethod = "getDefaultProduct")
    Product findById(@PathVariable Long id);
    @GetMapping("/products")
    @CircuitBreaker(name = "productService", fallbackMethod = "getDefaultProducts")
    List<Product> findAll();
    @PutMapping("/products/{id}")
    Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct);

    default Product getDefaultProduct(Long id,Exception exception){
        return Product.builder()
                .id(id)
                .title("Default")
                .price(100)
                .quantity(10)
                .build();
    }

    default List<Product> getDefaultProducts(Exception exception){
        return List.of();
    }
}
