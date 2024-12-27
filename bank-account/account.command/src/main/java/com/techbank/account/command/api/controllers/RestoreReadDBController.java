package com.techbank.account.command.api.controllers;

import com.techbank.account.command.api.commands.CloseAccountCommand;
import com.techbank.account.command.api.commands.RestoreReadDBCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispacther;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/restoreReadDB")
public class RestoreReadDBController {

    private final CommandDispacther commandDispacther;
    private static final Logger logger = Logger.getLogger(RestoreReadDBController.class.getName());

    @Autowired
    public RestoreReadDBController(CommandDispacther commandDispacther) {
        this.commandDispacther = commandDispacther;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDB() {
        try {
            commandDispacther.send(new RestoreReadDBCommand());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse("Read DB Restored!"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse("Something happened while restoring read db, reason: " + e.getMessage()));

        }
    }
}
