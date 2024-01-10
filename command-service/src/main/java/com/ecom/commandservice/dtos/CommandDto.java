package com.ecom.commandservice.dtos;

import com.ecom.commandservice.entities.Command;
import com.ecom.commandservice.model.Product;
import com.ecom.commandservice.model.User;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CommandDto {
    private Long id;
    private String description;
    private int quantity;
    private LocalDate createdAt;
    private double amount;
    private Product product;
    private User user;

}
