package com.techbank.account.query.infrastructure;

import com.techbank.cqrs.core.commands.BaseCommand;
import com.techbank.cqrs.core.commands.CommandHandler;
import com.techbank.cqrs.core.domain.BaseEntity;
import com.techbank.cqrs.core.infrastructure.QueryDispacther;
import com.techbank.cqrs.core.queries.BaseQuery;
import com.techbank.cqrs.core.queries.QueryHandler;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispacther {
    private final Map<Class<? extends BaseQuery>, QueryHandler<? extends BaseQuery>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandler<T> handler) {
        routes.put(type, handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        QueryHandler handler = routes.get(query.getClass());
        if (handler == null)
            throw new RuntimeException(MessageFormat.format("No handler found for the query {0}", query.getClass()));
        return handler.handle(query);
    }
}
