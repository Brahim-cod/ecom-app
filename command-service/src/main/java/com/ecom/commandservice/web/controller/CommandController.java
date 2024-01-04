package com.ecom.commandservice.web.controller;

import com.ecom.commandservice.entities.Command;
import com.ecom.commandservice.repository.CommandRepository;
import com.ecom.commandservice.service.CommandService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CommandController implements HealthIndicator {
    private CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @GetMapping("/lastcommands")
    public ResponseEntity<List<Command>> commandList(){
        return new ResponseEntity<>(commandService.getLastCommands(), HttpStatus.OK);
    }
    @GetMapping("/commands")
    public ResponseEntity<List<Command>> allCommands(){
        return new ResponseEntity<>(commandService.getAllCommands(), HttpStatus.OK);
    }
    @GetMapping("/commands/{id}")
    public ResponseEntity<Command> getCommandById(@PathVariable Long id) {
        Optional<Command> command = commandService.getCommandById(id);

        return command.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/commands")
    public ResponseEntity<Command> createCommand(@RequestBody Command command) {
        Command createdCommand = commandService.addCommand(command);
        return new ResponseEntity<>(createdCommand, HttpStatus.CREATED);
    }

    @PutMapping("/commands/{id}")
    public ResponseEntity<Command> updateCommand(@PathVariable Long id, @RequestBody Command updatedCommand) {
        try {
            Command updated = commandService.updateCommand(id, updatedCommand);
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

    @Override
    public Health health() {
        System.out.println("****** Actuator : CommandController health() ******");
        List<Command> commands = commandService.getAllCommands();
        if (commands.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}
