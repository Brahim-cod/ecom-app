package com.ecom.userservice.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String role;
}
