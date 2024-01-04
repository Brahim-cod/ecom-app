package com.ecom.commandservice.service;

import com.ecom.commandservice.configuration.ApplicationConfiguration;
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

    public CommandService(CommandRepository commandRepository, ApplicationConfiguration applicationConfiguration) {
        this.commandRepository = commandRepository;
        this.applicationConfiguration = applicationConfiguration;
    }

    public List<Command> getLastCommands() {
        int days = applicationConfiguration.getCommandesLast();
        System.out.println("************ CommandLast = " + days + " ***********");
        LocalDate startDate = LocalDate.now().minusDays(days);
        return commandRepository.findLastCommands(startDate);
    }
    public List<Command> getAllCommands(){
        return commandRepository.findAll();
    }
    public Optional<Command> getCommandById(Long id) {
        return commandRepository.findById(id);
    }
    public Command addCommand(Command command){
        return commandRepository.save(command);
    }
    public Command updateCommand(Long id, Command updatedCommand) {
        Optional<Command> existingCommandOptional = getCommandById(id);

        if (existingCommandOptional.isPresent()) {
            Command existingCommand = existingCommandOptional.get();

            existingCommand.setDescription(updatedCommand.getDescription());
            existingCommand.setQuantity(updatedCommand.getQuantity());
            existingCommand.setCreatedAt(updatedCommand.getCreatedAt());
            existingCommand.setAmount(updatedCommand.getAmount());

            return commandRepository.save(existingCommand);
        } else {
            throw new EntityNotFoundException("Command with id " + id + " not found");
        }
    }
    public void deleteCommand(Long id) {
        commandRepository.deleteById(id);
    }


}
