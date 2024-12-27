package com.techbank.account.command.infrastructure;

import com.techbank.account.command.domain.AccountAggregate;
import com.techbank.account.common.dto.AccountType;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.cqrs.core.infrastructure.EventStore;
import com.techbank.cqrs.core.producers.EventProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountEventSourcingHandlerTest {

    @Mock
    private EventStore eventStore;
    @Mock
    private EventProducer eventProducer;

    @Test
    void shouldSave() {
        AccountAggregate aggregate = mock(AccountAggregate.class);
        when(aggregate.getId()).thenReturn("id");
        when(aggregate.getUncommitedChanges()).thenReturn(emptyList());
        when(aggregate.getVersion()).thenReturn(1);

        new AccountEventSourcingHandler(eventStore, eventProducer).save(aggregate);

        verify(aggregate).markChangesAsCommitted();
    }

    @Test
    void shouldGetAnAggregateById() {
        String aggregateId = "aggregateId";
        int accountBalance = 1000;
        when(eventStore.getEvents(aggregateId)).thenReturn(
                List.of(new AccountOpenedEvent(
                        aggregateId,
                        1,
                        "holder",
                        AccountType.CURRENT,
                        new Date(),
                        accountBalance
                ))
        );
        AccountAggregate aggregate = new AccountEventSourcingHandler(eventStore, eventProducer).getById(aggregateId);

        assertNotNull(aggregate);
        assertThat(aggregate.getBalance()).isEqualTo(accountBalance);
        verify(eventStore).getEvents(aggregateId);
    }
}