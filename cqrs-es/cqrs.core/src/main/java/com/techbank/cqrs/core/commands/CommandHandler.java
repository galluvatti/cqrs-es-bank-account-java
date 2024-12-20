package com.techbank.cqrs.core.commands;

@FunctionalInterface
public interface CommandHandler<T extends BaseCommand> {
    void handle(T command);
}
