package com.techbank.account.query.domain.events;

import com.techbank.account.query.domain.AccountType;

import java.io.Serializable;
import java.util.Date;

public class AccountOpenedEvent implements Serializable {
    private String id;
    private int version;
    private String accountHolder;
    private AccountType accountType;
    private Date createdDate;
    private double openingBalance;

    public AccountOpenedEvent(String id, int version, String accountHolder, AccountType accountType, Date createdDate, double openingBalance) {
        this.id = id;
        this.version = version;
        this.accountHolder = accountHolder;
        this.accountType = accountType;
        this.createdDate = createdDate;
        this.openingBalance = openingBalance;
    }

    public AccountOpenedEvent() {
    }

    public String getId() {
        return id;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public int getVersion() {
        return version;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }
}

