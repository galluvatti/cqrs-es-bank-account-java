package com.techbank.account.command.infrastructure;

import com.techbank.account.command.domain.EventStoreRepository;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.events.EventModel;
import com.techbank.cqrs.core.exceptions.AggregateNotFoundException;
import com.techbank.cqrs.core.exceptions.OptimisticConcurrencyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountEventStoreTest {

    @Mock
    private EventStoreRepository eventStoreRepository;

    @Test
    void shouldSaveEvents() {
        int expectedVersion = 2;
        String aggregateId = "aggregateId";
        BaseEvent firstEvent = mock(BaseEvent.class);
        BaseEvent secondEvent = mock(BaseEvent.class);
        List<BaseEvent> eventsToSave = Arrays.asList(firstEvent, secondEvent);

        List<EventModel> pastEvents = anEventStream(expectedVersion);
        when(eventStoreRepository.findByAggregateId(aggregateId)).thenReturn(pastEvents);

        new AccountEventStore(eventStoreRepository).saveEvents(
                aggregateId,
                eventsToSave,
                expectedVersion
        );

        verify(eventStoreRepository, times(eventsToSave.size())).save(any());
    }

    @Test
    void shouldRaiseAnExceptionWhenAggregateVersionToSaveDoesNotMatch() {
        int expectedVersion = 2;
        String aggregateId = "aggregateId";
        BaseEvent firstEvent = mock(BaseEvent.class);
        BaseEvent secondEvent = mock(BaseEvent.class);
        List<BaseEvent> eventsToSave = Arrays.asList(firstEvent, secondEvent);

        List<EventModel> pastEvents = anEventStream(expectedVersion + 1);
        when(eventStoreRepository.findByAggregateId(aggregateId)).thenReturn(pastEvents);

        assertThatThrownBy(() -> new AccountEventStore(eventStoreRepository).saveEvents(
                aggregateId,
                eventsToSave,
                expectedVersion
        )).isInstanceOf(OptimisticConcurrencyException.class);

        verify(eventStoreRepository, times(0)).save(any());
    }

    @Test
    void shouldRetrieveEvents() {
        String aggregateId = "aggregateId";
        List<EventModel> pastEvents = anEventStream(2);
        when(eventStoreRepository.findByAggregateId(aggregateId)).thenReturn(pastEvents);

        List<BaseEvent> events = new AccountEventStore(eventStoreRepository).getEvents(aggregateId);

        assertThat(events.size()).isEqualTo(pastEvents.size());
    }

    @Test
    void shouldRaiseAnExceptionWhenAggregateIdDoesNotExist() {
        String aggregateId = "aggregateId";
        when(eventStoreRepository.findByAggregateId(aggregateId)).thenReturn(null);

        assertThatThrownBy(
                () -> new AccountEventStore(eventStoreRepository).getEvents(aggregateId)
        ).isInstanceOf(AggregateNotFoundException.class);

    }

    private List<EventModel> anEventStream(int size) {
        return IntStream.range(0, size)
                .mapToObj(AccountEventStoreTest::aMockEventModel)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static EventModel aMockEventModel(int version) {
        EventModel mock = mock(EventModel.class);
        lenient().when(mock.getVersion()).thenReturn(version + 1);
        lenient().when(mock.getEventData()).thenReturn(mock(BaseEvent.class));
        return mock;
    }
}