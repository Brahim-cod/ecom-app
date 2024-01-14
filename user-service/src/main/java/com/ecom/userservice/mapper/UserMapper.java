package com.ecom.userservice.mapper;

import com.ecom.userservice.dtos.UserDto;
import com.ecom.userservice.entities.User;
import com.ecom.userservice.enums.Role;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserDto convertToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .build();
    }
    public User convertToBto(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .role(Role.valueOf(userDto.getRole()))
                .build();
    }
}
