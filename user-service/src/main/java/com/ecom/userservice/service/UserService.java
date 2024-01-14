package com.ecom.userservice.service;

import com.ecom.userservice.dtos.UserConnectDto;
import com.ecom.userservice.dtos.UserDto;
import com.ecom.userservice.entities.User;
import com.ecom.userservice.mapper.UserMapper;
import com.ecom.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(User user) {
        // Add any business logic or validation before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.convertToDto(userRepository.save(user));
    }

    // Read operations
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.convertToDto(user))
                .toList();
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return Optional.of(userMapper.convertToDto(userRepository.findById(id).get()));
    }

    // Update operation
    @Override
    public UserDto updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    // Update fields based on your requirements
                    existingUser.setUsername(updatedUser.getUsername());
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setPhone(updatedUser.getPhone());

                    // Save and return the updated product
                    return userMapper.convertToDto(userRepository.save(existingUser));
                })
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    // Delete operation
    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public UserDto connectUser(UserConnectDto userConnectDto){
        var user = userRepository.findByUsername(userConnectDto.getUsername());

        if (user != null) {
            if(passwordEncoder.matches(userConnectDto.getPassword(), user.getPassword()))
                return userMapper.convertToDto(user);
        }

        return null;
    }
}
