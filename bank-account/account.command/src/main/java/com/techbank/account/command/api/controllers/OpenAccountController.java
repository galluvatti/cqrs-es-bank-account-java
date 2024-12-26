package com.techbank.account.command.api.controllers;

import com.techbank.account.command.api.commands.OpenAccountCommand;
import com.techbank.account.command.api.dto.OpenAccountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispacther;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {

    private final CommandDispacther commandDispacther;
    private static final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    public OpenAccountController(CommandDispacther commandDispacther) {
        this.commandDispacther = commandDispacther;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        try {
            String bankAccountId = UUID.randomUUID().toString();
            command.setId(bankAccountId);
            commandDispacther.send(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(new OpenAccountResponse("Bank account created.", bankAccountId));
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("Bank account not created for reason: " + e.getMessage()));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse("Bank account not created for reason: " + e.getMessage()));

        }
    }
}
