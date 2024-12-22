package com.techbank.account.command.infrastructure;

import com.techbank.account.command.domain.AccountAggregate;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.handlers.EventSourcingHandler;
import com.techbank.cqrs.core.infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private EventStore eventStore;

    @Autowired
    public AccountEventSourcingHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }


    @Override
    public void save(AccountAggregate aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommitedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String aggregateId) {
        AccountAggregate aggregate = new AccountAggregate();
        List<BaseEvent> events = eventStore.getEvents(aggregateId);
        if (!CollectionUtils.isEmpty(events)) {
            aggregate.replayEvents(events);
            //FIXME I don't like the setter
            aggregate.setVersion(events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder()).get());
        }
        return aggregate;
    }
}
