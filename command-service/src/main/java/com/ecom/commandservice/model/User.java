package com.ecom.commandservice.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
}
