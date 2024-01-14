package com.ecom.userservice;

import com.ecom.userservice.entities.User;
import com.ecom.userservice.enums.Role;
import com.ecom.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
    @Bean
    public  PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            User userAdmin = User.builder()
                    .username("brokly")
                    .password(passwordEncoder.encode("123456"))
                    .email("brokly@gmail.com")
                    .phone("+2126791354943")
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(userAdmin);

            User userClient = User.builder()
                    .username("brahim")
                    .password(passwordEncoder.encode("123456"))
                    .email("brahim@gmail.com")
                    .phone("+2126734597943")
                    .role(Role.USER)
                    .build();
            userRepository.save(userClient);
        };
    }
}
