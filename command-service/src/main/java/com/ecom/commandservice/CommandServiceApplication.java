package com.ecom.commandservice;

import com.ecom.commandservice.configuration.ApplicationConfiguration;
import com.ecom.commandservice.entities.Command;
import com.ecom.commandservice.repository.CommandRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class CommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CommandRepository commandRepository){
        return args -> {
            Random random = new Random();

            for (int i = 1; i <= 30; i++) {
                Command command = Command.builder()
                        .description("Command Description " + i)
                        .quantity(random.nextInt(10) + 1)
                        .createdAt(LocalDate.now().minusDays(i))
                        .amount(generateRandomDouble(100,500,random))
                        .build();
                commandRepository.save(command);
            }
        };
    }

    public static double generateRandomDouble(double min, double max, Random random) {
        return min + (max - min) * random.nextDouble();
    }
}
