package com.techbank.cqrs.core.handlers;

public interface EventSourcingHandler<T> {
    void save(T aggregate);
    T getById(String aggregateId);
}
