package com.ecom.userservice.web.controller;

import com.ecom.userservice.dtos.UserConnectDto;
import com.ecom.userservice.dtos.UserDto;
import com.ecom.userservice.entities.User;
import com.ecom.userservice.service.IUserService;
import com.ecom.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }
    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        var createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Read operations
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        var users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update operation
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        var updated = userService.updateUser(id, updatedUser);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Delete operation
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody UserConnectDto userConnectDto) {
        var userDto = userService.connectUser(userConnectDto);

        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
