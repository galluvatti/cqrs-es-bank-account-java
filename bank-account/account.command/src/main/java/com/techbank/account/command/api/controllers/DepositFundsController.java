package com.techbank.account.command.api.controllers;

import com.techbank.account.command.api.commands.DepositFundsCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispacther;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/depositFunds")
public class DepositFundsController {

    private final CommandDispacther commandDispacther;
    private static final Logger logger = Logger.getLogger(DepositFundsController.class.getName());

    @Autowired
    public DepositFundsController(CommandDispacther commandDispacther) {
        this.commandDispacther = commandDispacther;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> openAccount(@PathVariable(value = "id") String id,
                                                    @RequestBody DepositFundsCommand command) {
        try {
            command.setId(id);
            commandDispacther.send(command);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse("Funds successfully deposited"));
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("Funds not deposited for reason: " + e.getMessage()));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse("Funds not deposited for reason for reason: " + e.getMessage()));

        }
    }
}
