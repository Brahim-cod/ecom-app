package com.ecom.commandservice.service;

import com.ecom.commandservice.configuration.ApplicationConfiguration;
import com.ecom.commandservice.entities.Command;
import com.ecom.commandservice.repository.CommandRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
        System.out.println("************ CommandLast = "+ days + " ***********");
        LocalDate startDate = LocalDate.now().minusDays(days);
        return commandRepository.findLastCommands(startDate);
    }
    public List<Command> getAllCommands(){
        return commandRepository.findAll();
    }


}
