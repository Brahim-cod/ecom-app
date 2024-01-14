package com.ecom.userservice.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserConnectDto {
    private String username;
    private String password;
}
