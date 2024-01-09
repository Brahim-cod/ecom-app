package com.ecom.commandservice.dtos;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CommandRegistrationgDto {
    private String description;
    private int quantity;
    private LocalDate createdAt;
    private double amount;
    private Long productId;
}
