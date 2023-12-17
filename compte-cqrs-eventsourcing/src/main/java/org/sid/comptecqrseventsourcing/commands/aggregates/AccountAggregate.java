package org.sid.comptecqrseventsourcing.commands.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.sid.comptecqrseventsourcing.commonapi.commands.CreateAccountCommand;
import org.sid.comptecqrseventsourcing.commonapi.commands.CreditAccountCommand;
import org.sid.comptecqrseventsourcing.commonapi.commands.DebitAccountCommand;
import org.sid.comptecqrseventsourcing.commonapi.enums.AccountStatus;
import org.sid.comptecqrseventsourcing.commonapi.events.AccountActivatedEvent;
import org.sid.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import org.sid.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import org.sid.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import org.sid.comptecqrseventsourcing.commonapi.exceptions.BalanceInSufficientException;
import org.sid.comptecqrseventsourcing.commonapi.exceptions.NegativeAmountException;
import org.sid.comptecqrseventsourcing.commonapi.exceptions.NegativeInitialBalanceException;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus accountStatus;

    public AccountAggregate() {
        // required by AXON
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {

        if(command.getInitialeBalance() <0) throw new NegativeInitialBalanceException("Balance should not be negative !");

        AggregateLifecycle.apply(new AccountCreatedEvent(
            command.getId(),
            command.getInitialeBalance(),
            command.getCurrency(),
            AccountStatus.CREATED
        ));
    }


    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
       this.accountId = event.getId();
       this.balance = event.getInitialeBalance();
       this.currency = event.getCurrency();
       this.accountStatus = event.getAccountStatus();

       AggregateLifecycle.apply(new AccountActivatedEvent(
           event.getId(),
           AccountStatus.ACTIVATED
       ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        this.accountStatus = event.getAccountStatus();

    }

    @CommandHandler
    public void handle(CreditAccountCommand command) {
        if(command.getAmount() <0) throw new NegativeAmountException("Amount should not be negative");

        AggregateLifecycle.apply(new AccountCreditedEvent(
            command.getId(),
            command.getAmount(),
            command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command) {

        if(command.getAmount() <0) throw new NegativeAmountException("Amount should not be negative");
        if(balance < command.getAmount() ) throw new BalanceInSufficientException("Balance is InSufficient !");

        AggregateLifecycle.apply(new AccountDebitedEvent(
            command.getId(),
            command.getAmount(),
            command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getAmount();
    }

}
