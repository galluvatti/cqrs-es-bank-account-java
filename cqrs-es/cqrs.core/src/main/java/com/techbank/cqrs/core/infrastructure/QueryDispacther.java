package com.techbank.cqrs.core.infrastructure;

import com.techbank.cqrs.core.domain.BaseEntity;
import com.techbank.cqrs.core.queries.BaseQuery;
import com.techbank.cqrs.core.queries.QueryHandler;

import java.util.List;

public interface QueryDispacther {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandler<T> handler);

    <U extends BaseEntity> List<U> send(BaseQuery query);
}
