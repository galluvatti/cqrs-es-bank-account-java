package com.techbank.account.query.api.queries;

import com.techbank.cqrs.core.queries.BaseQuery;

public class FindAccountsWithBalanceQuery extends BaseQuery {
    private final double balance;
    private final EqualityType equalityType;

    public FindAccountsWithBalanceQuery(double balance, EqualityType equalityType) {
        this.balance = balance;
        this.equalityType = equalityType;
    }

    public double getBalance() {
        return balance;
    }

    public EqualityType getEqualityType() {
        return equalityType;
    }
}
