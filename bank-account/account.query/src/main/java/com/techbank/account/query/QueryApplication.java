package com.techbank.account.query;

import com.techbank.account.query.api.queries.*;
import com.techbank.cqrs.core.infrastructure.QueryDispacther;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QueryApplication {

	@Autowired
	private QueryDispacther queryDispacther;
	@Autowired
	private QueryHandler queryHandler;

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}


	@PostConstruct
	public void registerHandlers() {
		queryDispacther.registerHandler(FindAllAccountsQuery.class, queryHandler::handle);
		queryDispacther.registerHandler(FindAccountByIdQuery.class, queryHandler::handle);
		queryDispacther.registerHandler(FindAccountsWithBalanceQuery.class, queryHandler::handle);
		queryDispacther.registerHandler(FindAccountByHolderQuery.class, queryHandler::handle);
	}
}
