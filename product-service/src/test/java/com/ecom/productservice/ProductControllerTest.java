package com.ecom.productservice;

import com.ecom.productservice.entities.Product;
import com.ecom.productservice.service.IProductService;
import com.ecom.productservice.web.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Mock
    private IProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void createProduct_ShouldReturnCreatedProduct() {
        // Arrange
        Product mockProduct = new Product();
        when(productService.createProduct(any())).thenReturn(mockProduct);

        // Act
        ResponseEntity<Product> response = productController.createProduct(new Product());

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        // Arrange
        List<Product> mockProductList = Collections.singletonList(new Product());
        when(productService.getAllProducts()).thenReturn(mockProductList);

        // Act
        ResponseEntity<List<Product>> response = productController.getAllProducts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProductList, response.getBody());
    }

    @Test
    void getProductsByCategory_ShouldReturnProductsByCategory() {
        // Arrange
        Long categoryId = 1L;
        List<Product> mockProductList = Collections.singletonList(new Product());
        when(productService.getProductsByCategory(categoryId)).thenReturn(mockProductList);

        // Act
        ResponseEntity<List<Product>> response = productController.getProductsByCategory(categoryId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProductList, response.getBody());
    }

    @Test
    void getProductById_ExistingId_ShouldReturnProduct() {
        // Arrange
        Long existingId = 1L;
        Product mockProduct = new Product();
        when(productService.getProductById(existingId)).thenReturn(Optional.of(mockProduct));

        // Act
        ResponseEntity<Product> response = productController.getProductById(existingId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
    }

    @Test
    void getProductById_NonExistingId_ShouldReturnNotFound() {
        // Arrange
        Long nonExistingId = 2L;
        when(productService.getProductById(nonExistingId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Product> response = productController.getProductById(nonExistingId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Add more tests for other methods as needed

    // ...

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {
        // Arrange
        Long productId = 1L;
        Product updatedProduct = new Product();
        when(productService.updateProduct(productId, updatedProduct)).thenReturn(updatedProduct);

        // Act
        ResponseEntity<Product> response = productController.updateProduct(productId, updatedProduct);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProduct, response.getBody());
    }

    @Test
    void deleteProduct_ShouldReturnNoContent() {
        // Arrange
        Long productId = 1L;

        // Act
        ResponseEntity<Void> response = productController.deleteProduct(productId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(productId);
    }
}
