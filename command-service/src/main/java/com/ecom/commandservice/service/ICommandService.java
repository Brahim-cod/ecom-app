package com.ecom.commandservice.service;

import com.ecom.commandservice.dtos.CommandDto;
import com.ecom.commandservice.dtos.CommandRegistrationgDto;

import java.util.List;
import java.util.Optional;

public interface ICommandService {
    List<CommandDto> getLastCommands();

    List<CommandDto> getAllCommands();

    List<CommandDto> getAllCommandsByUser(Long userId);

    Optional<CommandDto> getCommandById(Long id);

    CommandDto addCommand(CommandRegistrationgDto command);

    CommandDto updateCommand(Long id, CommandRegistrationgDto updatedCommand);

    void deleteCommand(Long id);
}
