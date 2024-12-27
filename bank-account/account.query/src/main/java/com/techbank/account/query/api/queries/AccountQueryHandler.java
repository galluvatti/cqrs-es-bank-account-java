package com.techbank.account.query.api.queries;

import com.techbank.account.query.domain.BankAccount;
import com.techbank.account.query.domain.BankAccountRepository;
import com.techbank.cqrs.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountQueryHandler implements QueryHandler {

    private final BankAccountRepository repository;

    @Autowired
    public AccountQueryHandler(BankAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccounts = repository.findAll();
        List<BaseEntity> result = new ArrayList<>();
        bankAccounts.forEach(result::add);
        return result;
    }

    @Override
    public List<BaseEntity> handle(FindAccountsWithBalanceQuery query) {
        return query.getEqualityType() == EqualityType.GREATER_THAN ?
                repository.findByBalanceGreaterThan(query.getBalance()) :
                repository.findByBalanceLessThan(query.getBalance());
    }


    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        Optional<BankAccount> bankAccount = repository.findByAccountHolder(query.getHolder());
        if (bankAccount.isEmpty()) return Collections.emptyList();
        ArrayList<BaseEntity> result = new ArrayList<>();
        result.add(bankAccount.get());
        return result;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        Optional<BankAccount> bankAccount = repository.findById(query.getId());
        if (bankAccount.isEmpty()) return Collections.emptyList();
        ArrayList<BaseEntity> result = new ArrayList<>();
        result.add(bankAccount.get());
        return result;
    }
}
