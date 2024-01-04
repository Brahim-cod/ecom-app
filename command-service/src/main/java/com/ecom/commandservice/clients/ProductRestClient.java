package com.ecom.commandservice.clients;

import com.ecom.commandservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductRestClient {
    @GetMapping("/products/{id}")
    Product findById(@PathVariable Long id);
    @GetMapping("/products")
    List<Product> findAll();
}
