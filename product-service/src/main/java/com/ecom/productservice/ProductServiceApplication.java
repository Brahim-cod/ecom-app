package com.ecom.productservice;

import com.ecom.productservice.entities.Category;
import com.ecom.productservice.entities.Product;
import com.ecom.productservice.repository.CategoryRepository;
import com.ecom.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository, CategoryRepository categoryRepository){
        return args -> {
            Random random = new Random();

            for (int i = 1; i <= 10; i++){
                Category category = Category.builder()
                        .name("Category " + i)
                        .build();
                categoryRepository.save(category);

                Product product = Product.builder()
                        .title("Product Title " + i)
                        .price(generateRandomDouble(10, 200, random))
                        .quantity(random.nextInt(50) + 1)
                        .category(category)
                        .build();
                productRepository.save(product);
            }
        };
    }

    public static double generateRandomDouble(double min, double max, Random random) {
        return Math.floor(min + (max - min) * random.nextDouble());
    }

}
