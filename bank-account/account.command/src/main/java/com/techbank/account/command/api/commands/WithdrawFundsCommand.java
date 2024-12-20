package com.techbank.account.command.api.commands;

import com.techbank.cqrs.core.commands.BaseCommand;

public class WithdrawFundsCommand extends BaseCommand {
    private double amount;

    public WithdrawFundsCommand(String id, double amount) {
        super(id);
        this.amount = amount;
    }
}
