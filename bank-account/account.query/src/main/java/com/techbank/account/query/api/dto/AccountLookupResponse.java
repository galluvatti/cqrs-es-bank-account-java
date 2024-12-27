package com.techbank.account.query.api.dto;

import com.techbank.account.common.dto.BaseResponse;
import com.techbank.account.query.domain.BankAccount;

import java.util.List;

public class AccountLookupResponse extends BaseResponse {
    private List<BankAccount> bankAccounts;

    public AccountLookupResponse(String message, List<BankAccount> bankAccounts) {
        super(message);
        this.bankAccounts = bankAccounts;
    }

    public AccountLookupResponse(String message) {
        super(message);
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }
}
