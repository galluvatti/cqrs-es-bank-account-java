package com.techbank.account.query.api.queries;

import com.techbank.cqrs.core.queries.BaseQuery;

public class FindAccountByIdQuery extends BaseQuery {
    private final String id;

    public FindAccountByIdQuery(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
