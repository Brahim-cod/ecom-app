package com.ecom.commandservice;

import com.ecom.commandservice.clients.ProductRestClient;
import com.ecom.commandservice.configuration.ApplicationConfiguration;
import com.ecom.commandservice.entities.Command;
import com.ecom.commandservice.model.Product;
import com.ecom.commandservice.repository.CommandRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
@EnableFeignClients
public class CommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CommandRepository commandRepository, ProductRestClient productRestClient){
        return args -> {
            Random random = new Random();

            var products = productRestClient.findAll();

            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                var quantity = random.nextInt(product.getQuantity()) + 1;
                Command command = Command.builder()
                        .description("Command Description " + product.getId() + " for Product : " + product.getTitle())
                        .quantity(quantity)
                        .createdAt(LocalDate.now().minusDays(i + 1))
                        .amount(generateamount(product.getPrice(), quantity))
                        .productId(product.getId())
                        .build();
                commandRepository.save(command);
            }
        };
    }

    public static double generateamount(double price, int quantity) {
        return price * quantity;
    }
}
