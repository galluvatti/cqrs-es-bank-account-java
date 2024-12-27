package com.techbank.account.command.infrastructure;

import com.techbank.account.command.domain.AccountAggregate;
import com.techbank.account.command.domain.EventStoreRepository;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.events.EventModel;
import com.techbank.cqrs.core.exceptions.AggregateNotFoundException;
import com.techbank.cqrs.core.exceptions.OptimisticConcurrencyException;
import com.techbank.cqrs.core.infrastructure.EventStore;
import com.techbank.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountEventStore implements EventStore {

    private final EventStoreRepository repository;
    private final EventProducer eventProducer;

    @Autowired
    public AccountEventStore(EventStoreRepository repository, EventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        List<EventModel> eventStream = repository.findByAggregateId(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new OptimisticConcurrencyException("Version Mismatch, expected " + expectedVersion + " but found " +
                    eventStream.get(eventStream.size() - 1).getVersion());
        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            repository.save(new EventModel(
                    new Date(),
                    aggregateId,
                    AccountAggregate.class.getTypeName(),
                    version,
                    event.getClass().getTypeName(),
                    event
            ));
            eventProducer.produce(event.getClass().getSimpleName(), event);
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        List<EventModel> eventStream = repository.findByAggregateId(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException();
        }
        return eventStream.stream().map(EventModel::getEventData).toList();
    }

    @Override
    public List<String> getAggregateIds() {
        return repository.findAll().stream().map(EventModel::getAggregateId).distinct().toList();
    }
}
