package com.ecom.commandservice.web.controller;

import com.ecom.commandservice.dtos.CommandDto;
import com.ecom.commandservice.dtos.CommandRegistrationgDto;
import com.ecom.commandservice.entities.Command;
import com.ecom.commandservice.service.CommandService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class CommandController implements HealthIndicator {
    private CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @GetMapping("/lastcommands")
    @CircuitBreaker(name = "commandService", fallbackMethod = "getDefaultCommands")
    public ResponseEntity<List<CommandDto>> commandList(){
        return new ResponseEntity<>(commandService.getLastCommands(), HttpStatus.OK);
    }

    @GetMapping("/commands")
    @CircuitBreaker(name = "commandService", fallbackMethod = "getDefaultCommands")
    public ResponseEntity<List<CommandDto>> allCommands(){
        return new ResponseEntity<>(commandService.getAllCommands(), HttpStatus.OK);
    }

    @GetMapping("/commands/{id}")
    @CircuitBreaker(name = "commandService", fallbackMethod = "getDefaultCommand")
    public ResponseEntity<CommandDto> getCommandById(@PathVariable Long id) {
        Optional<CommandDto> command = commandService.getCommandById(id);

        return command.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/commands")
    public ResponseEntity<CommandDto> createCommand(@RequestBody CommandRegistrationgDto command) {
        CommandDto createdCommand = commandService.addCommand(command);
        return new ResponseEntity<>(createdCommand, HttpStatus.CREATED);
    }

    @PutMapping("/commands/{id}")
    public ResponseEntity<CommandDto> updateCommand(@PathVariable Long id, @RequestBody CommandRegistrationgDto updatedCommand) {
        try {
            CommandDto updated = commandService.updateCommand(id, updatedCommand);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/commands/{id}")
    public ResponseEntity<Void> deleteCommand(@PathVariable Long id) {
        commandService.deleteCommand(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public List<CommandDto> getDefaultCommands(Exception exception){
        return List.of();
    }
    public CommandDto getDefaultCommand(Long id, Exception exception){
        return CommandDto.builder()
                .id(id)
                .description("Default")
                .quantity(10)
                .amount(100)
                .createdAt(LocalDate.now())
                .product(null)
                .build();
    }

    @Override
    public Health health() {
        System.out.println("****** Actuator : CommandController health() ******");
        List<CommandDto> commands = commandService.getAllCommands();
        if (commands.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}
