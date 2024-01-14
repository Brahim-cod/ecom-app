package com.ecom.commandservice.service;

import com.ecom.commandservice.clients.ProductRestClient;
import com.ecom.commandservice.clients.UserRestClient;
import com.ecom.commandservice.configuration.ApplicationConfiguration;
import com.ecom.commandservice.dtos.CommandDto;
import com.ecom.commandservice.dtos.CommandRegistrationgDto;
import com.ecom.commandservice.entities.Command;
import com.ecom.commandservice.mapper.CommandMapper;
import com.ecom.commandservice.model.Product;
import com.ecom.commandservice.repository.CommandRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.IntBinaryOperator;

@Service
public class CommandService implements ICommandService {
    private CommandRepository commandRepository;
    private ApplicationConfiguration applicationConfiguration;
    private ProductRestClient productRestClient;
    private UserRestClient userRestClient;
    private CommandMapper commandMapper;

    public CommandService(CommandRepository commandRepository, ApplicationConfiguration applicationConfiguration, ProductRestClient productRestClient, UserRestClient userRestClient, CommandMapper commandMapper) {
        this.commandRepository = commandRepository;
        this.applicationConfiguration = applicationConfiguration;
        this.productRestClient = productRestClient;
        this.userRestClient = userRestClient;
        this.commandMapper = commandMapper;
    }


    @Override
    public List<CommandDto> getLastCommands() {
        int days = applicationConfiguration.getCommandesLast();
        System.out.println("************ CommandLast = " + days + " ***********");
        LocalDate startDate = LocalDate.now().minusDays(days);
        var commands = commandRepository.findLastCommands(startDate).
                stream()
                .map(command -> commandMapper.convertToDto(command)).toList();
        return commands;
    }
    @Override
    public List<CommandDto> getAllCommands(){
        var commands = commandRepository.findAll().
                stream()
                .map(command -> commandMapper.convertToDto(command)).toList();
        return commands;
    }
    @Override
    public List<CommandDto> getAllCommandsByUser(Long userId){
        var commands = commandRepository.findAllByUserId(userId).
                stream()
                .map(command -> commandMapper.convertToDto(command)).toList();
        return commands;
    }
    @Override
    public Optional<CommandDto> getCommandById(Long id) {
        return Optional.of(commandMapper.convertToDto(commandRepository.findById(id).get()));
    }
    @Override
    public CommandDto addCommand(CommandRegistrationgDto command){
        var product = productRestClient.findById(command.getProductId());
        var user = userRestClient.findById(command.getUserId());
        if (product != null && user != null){
            ChangeProductQuantity(product, command.getQuantity(), ((current, toAdd) -> current - toAdd));
            return commandMapper.convertToDto(commandRepository.save(commandMapper.convertToBto(command)));
        }
        else
            throw new RuntimeException("Product/User Not Found");
    }
    @Override
    public CommandDto updateCommand(Long id, CommandRegistrationgDto updatedCommand) {
        Optional<CommandDto> existingCommandOptional = getCommandById(id);

        if (existingCommandOptional.isPresent()) {
            Command existingCommand = commandMapper.convertToBto(existingCommandOptional.get());

            var productCommanded = productRestClient.findById(existingCommand.getProductId());

            ChangeProductQuantity(productCommanded, existingCommand.getQuantity(), ((current, toAdd) -> current + toAdd));

            existingCommand.setDescription(updatedCommand.getDescription());
            existingCommand.setQuantity(updatedCommand.getQuantity());
            existingCommand.setCreatedAt(updatedCommand.getCreatedAt());
            existingCommand.setAmount(updatedCommand.getAmount());
            existingCommand.setProductId(updatedCommand.getProductId());

            var product = productRestClient.findById(existingCommand.getProductId());

            ChangeProductQuantity(product, existingCommand.getQuantity(), ((current, toAdd) -> current - toAdd));

            return commandMapper.convertToDto(commandRepository.save(existingCommand));
        } else {
            throw new EntityNotFoundException("Command with id " + id + " not found");
        }
    }
    @Override
    public void deleteCommand(Long id) {
        commandRepository.deleteById(id);
    }

    private void ChangeProductQuantity(Product product, int quantity, IntBinaryOperator operation){
        int newQuantity = operation.applyAsInt(product.getQuantity(), quantity);
        product.setQuantity(newQuantity);
        productRestClient.updateProduct(product.getId(), product);
    }


}
