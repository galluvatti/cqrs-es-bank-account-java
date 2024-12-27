package com.techbank.account.query.api.queries;

import com.techbank.cqrs.core.queries.BaseQuery;

public class FindAccountByHolderQuery extends BaseQuery {
    private final String holder;

    public FindAccountByHolderQuery(String holder) {
        this.holder = holder;
    }

    public String getHolder() {
        return holder;
    }
}
