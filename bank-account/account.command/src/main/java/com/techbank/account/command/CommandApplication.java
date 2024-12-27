package com.techbank.account.command;

import com.techbank.account.command.api.commands.*;
import com.techbank.cqrs.core.infrastructure.CommandDispacther;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandApplication {

    @Autowired
    private CommandHandler commandHandler;
    @Autowired
    private CommandDispacther commandDispacther;

    public static void main(String[] args) {
        SpringApplication.run(CommandApplication.class, args);
    }

    @PostConstruct
    public void registerCommands() {
        commandDispacther.registerHandler(OpenAccountCommand.class, commandHandler::handle);
        commandDispacther.registerHandler(DepositFundsCommand.class, commandHandler::handle);
        commandDispacther.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
        commandDispacther.registerHandler(CloseAccountCommand.class, commandHandler::handle);
        commandDispacther.registerHandler(RestoreReadDBCommand.class, commandHandler::handle);
    }

}
