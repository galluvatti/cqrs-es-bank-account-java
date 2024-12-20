package com.techbank.account.command.api.commands;

import com.techbank.cqrs.core.commands.BaseCommand;

public class DepositFundsCommand extends BaseCommand {
    private double amount;

    public DepositFundsCommand(String id, double amount) {
        super(id);
        this.amount = amount;
    }
}
