package com.ecom.commandservice.service;

import com.ecom.commandservice.clients.ProductRestClient;
import com.ecom.commandservice.configuration.ApplicationConfiguration;
import com.ecom.commandservice.dtos.CommandDto;
import com.ecom.commandservice.entities.Command;
import com.ecom.commandservice.repository.CommandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommandService {
    private CommandRepository commandRepository;
    private ApplicationConfiguration applicationConfiguration;
    private ProductRestClient productRestClient;

    public CommandService(CommandRepository commandRepository, ApplicationConfiguration applicationConfiguration, ProductRestClient productRestClient) {
        this.commandRepository = commandRepository;
        this.applicationConfiguration = applicationConfiguration;
        this.productRestClient = productRestClient;
    }


    public List<CommandDto> getLastCommands() {
        int days = applicationConfiguration.getCommandesLast();
        System.out.println("************ CommandLast = " + days + " ***********");
        LocalDate startDate = LocalDate.now().minusDays(days);
        var commands = commandRepository.findLastCommands(startDate).
                stream()
                .map(command -> {
                    var converted = CommandDto.convertToDto(command);
                    converted.setProduct(productRestClient.findById(command.getProductId()));
                    return converted;
                } ).toList();
        return commands;
    }
    public List<CommandDto> getAllCommands(){
        var commands = commandRepository.findAll().
                stream()
                .map(command -> {
                    var converted = CommandDto.convertToDto(command);
                    converted.setProduct(productRestClient.findById(command.getProductId()));
                    return converted;
                } ).toList();
        return commands;
    }
    public Optional<CommandDto> getCommandById(Long id) {
        return Optional.of(CommandDto.convertToDto(commandRepository.findById(id).get()));
    }
    public CommandDto addCommand(Command command){
        return CommandDto.convertToDto(commandRepository.save(command));
    }
    public CommandDto updateCommand(Long id, Command updatedCommand) {
        Optional<CommandDto> existingCommandOptional = getCommandById(id);

        if (existingCommandOptional.isPresent()) {
            Command existingCommand = CommandDto.convertToBto(existingCommandOptional.get());

            existingCommand.setDescription(updatedCommand.getDescription());
            existingCommand.setQuantity(updatedCommand.getQuantity());
            existingCommand.setCreatedAt(updatedCommand.getCreatedAt());
            existingCommand.setAmount(updatedCommand.getAmount());

            return CommandDto.convertToDto(commandRepository.save(existingCommand));
        } else {
            throw new EntityNotFoundException("Command with id " + id + " not found");
        }
    }
    public void deleteCommand(Long id) {
        commandRepository.deleteById(id);
    }


}
