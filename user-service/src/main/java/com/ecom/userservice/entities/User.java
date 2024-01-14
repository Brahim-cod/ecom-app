package com.ecom.userservice.entities;

import com.ecom.userservice.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString @Builder
@Entity
@Table(name = "\"user\"")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    @Enumerated(value = EnumType.STRING)
    private Role role;
}