package com.ecom.commandservice.dtos;

import com.ecom.commandservice.entities.Command;
import com.ecom.commandservice.model.Product;
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
    public static CommandDto convertToDto(Command command){
        return CommandDto.builder()
                .id(command.getId())
                .description(command.getDescription())
                .quantity(command.getQuantity())
                .createdAt(command.getCreatedAt())
                .amount(command.getAmount())
                .build();
    }
    public static Command convertToBto(CommandDto commanddto){
        return Command.builder()
                .id(commanddto.getId())
                .description(commanddto.getDescription())
                .quantity(commanddto.getQuantity())
                .createdAt(commanddto.getCreatedAt())
                .amount(commanddto.getAmount())
                .productId(commanddto.getProduct().getId())
                .build();
    }
}
