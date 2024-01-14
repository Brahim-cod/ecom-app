package com.ecom.userservice.service;

import com.ecom.userservice.dtos.UserConnectDto;
import com.ecom.userservice.dtos.UserDto;
import com.ecom.userservice.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    UserDto createUser(User user);

    // Read operations
    List<UserDto> getAllUsers();

    Optional<UserDto> getUserById(Long id);

    // Update operation
    UserDto updateUser(Long id, User updatedUser);

    // Delete operation
    void deleteUser(Long id);

    UserDto connectUser(UserConnectDto userConnectDto);
}
