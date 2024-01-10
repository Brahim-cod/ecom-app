package com.ecom.userservice;

import com.ecom.userservice.entities.User;
import com.ecom.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){
        return args -> {
            User user = User.builder()
                    .username("brokly")
                    .password("123456")
                    .email("brokly@gmail.com")
                    .phone("+2126791354943")
                    .build();
            userRepository.save(user);
        };
    }
}
