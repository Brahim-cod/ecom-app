package com.ecom.commandservice.clients;

import com.ecom.commandservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "USER-SERVICE")
public interface UserRestClient {
    @GetMapping("/users/{id}")
    User findById(@PathVariable Long id);
    @GetMapping("/users")
    List<User> findAll();
    @PutMapping("/products/{id}")
    User updateUser(@PathVariable Long id, @RequestBody User updatedUser);

}
