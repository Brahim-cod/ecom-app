package com.ecom.commandservice.mapper;

import com.ecom.commandservice.clients.ProductRestClient;
import com.ecom.commandservice.dtos.CommandDto;
import com.ecom.commandservice.dtos.CommandRegistrationgDto;
import com.ecom.commandservice.entities.Command;
import org.springframework.stereotype.Service;

@Service
public class CommandMapper {
    private ProductRestClient productRestClient;

    public CommandMapper(ProductRestClient productRestClient) {
        this.productRestClient = productRestClient;
    }

    public CommandDto convertToDto(Command command){
        return CommandDto.builder()
                .id(command.getId())
                .description(command.getDescription())
                .quantity(command.getQuantity())
                .createdAt(command.getCreatedAt())
                .amount(command.getAmount())
                .product(productRestClient.findById(command.getProductId()))
                .build();
    }
    public Command convertToBto(CommandDto commanddto){
        return Command.builder()
                .id(commanddto.getId())
                .description(commanddto.getDescription())
                .quantity(commanddto.getQuantity())
                .createdAt(commanddto.getCreatedAt())
                .amount(commanddto.getAmount())
                .productId(commanddto.getProduct().getId())
                .build();
    }
    public Command convertToBto(CommandRegistrationgDto commandRegistrationDto){
        return Command.builder()
                .description(commandRegistrationDto.getDescription())
                .quantity(commandRegistrationDto.getQuantity())
                .createdAt(commandRegistrationDto.getCreatedAt())
                .amount(commandRegistrationDto.getAmount())
                .productId(commandRegistrationDto.getProductId())
                .build();
    }
}
