package com.ecom.commandservice.repository;

import com.ecom.commandservice.entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Long> {
    @Query("SELECT c FROM Command c WHERE c.createdAt >= :startDate")
    List<Command> findLastCommands(LocalDate startDate);
}
