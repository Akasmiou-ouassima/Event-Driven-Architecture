package org.sid.comptecqrseventsourcing.query.services;


import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.sid.comptecqrseventsourcing.commonapi.enums.TransactionType;
import org.sid.comptecqrseventsourcing.commonapi.events.AccountActivatedEvent;
import org.sid.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import org.sid.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import org.sid.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import org.sid.comptecqrseventsourcing.commonapi.queries.GetAccountByIdQuery;
import org.sid.comptecqrseventsourcing.commonapi.queries.GetAllAccountsQuery;
import org.sid.comptecqrseventsourcing.query.entities.Account;
import org.sid.comptecqrseventsourcing.query.entities.AccountTransaction;
import org.sid.comptecqrseventsourcing.query.repositories.AccountRepository;
import org.sid.comptecqrseventsourcing.query.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public AccountServiceHandler(AccountRepository accountRepository, TransactionRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = operationRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage<AccountCreditedEvent> eventMessage)
    {
        log.info("******************************************");
        log.info("Handling AccountCreatedEvent: " + event.getId());
        log.info("AccountCreatedEvent recieved!");
        Account account = new Account();
        account.setId(event.getId());
        account.setAccountStatus(event.getAccountStatus());
        account.setBalance(event.getInitialeBalance());
        account.setCurrency(event.getCurrency());
        account.setCreatedAt(eventMessage.getTimestamp());

        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event)
    {
        log.info("AccountActivatedEvent recieved!");
        Account account = accountRepository.findById(event.getId()).get();
        account.setAccountStatus(event.getAccountStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event)
    {
        log.info("AccountCreditedEvent recieved!");
        Account account = accountRepository.findById(event.getId()).get();
        AccountTransaction operation = new AccountTransaction();
        operation.setTransactionType(TransactionType.CREDIT);
        operation.setAmount(event.getAmount());
        operation.setTimestamp(new Date());
        operation.setAccount(account);
        transactionRepository.save(operation);
        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);
    }


    @EventHandler
    public void on(AccountDebitedEvent event)
    {
        log.info("AccountDebitedEvent recieved!");
        Account account = accountRepository.findById(event.getId()).get();
        AccountTransaction operation = new AccountTransaction();
        operation.setTransactionType(TransactionType.DEBIT);
        operation.setAmount(event.getAmount());
        operation.setTimestamp(new Date());
        operation.setAccount(account);
        transactionRepository.save(operation);
        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query) {
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountByIdQuery query) {
        return accountRepository.findById(query.getId()).get();
    }

}
