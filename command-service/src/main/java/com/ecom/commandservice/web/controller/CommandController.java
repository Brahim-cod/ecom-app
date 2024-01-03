package com.ecom.commandservice.web.controller;

import com.ecom.commandservice.entities.Command;
import com.ecom.commandservice.repository.CommandRepository;
import com.ecom.commandservice.service.CommandService;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommandController implements HealthIndicator {
    private CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @GetMapping("/lastcommands")
    public List<Command> commandList(){
        return commandService.getLastCommands();
    }
    @GetMapping("/commands")
    public List<Command> allCommands(){
        return commandService.getAllCommands();
    }

    @Override
    public Health health() {
        System.out.println("****** Actuator : CommandController health() ");
        List<Command> commands = commandService.getAllCommands();
        if (commands.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}
