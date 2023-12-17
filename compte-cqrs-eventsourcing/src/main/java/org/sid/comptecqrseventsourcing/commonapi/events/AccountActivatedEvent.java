package org.sid.comptecqrseventsourcing.commonapi.events;

import lombok.Getter;
import org.sid.comptecqrseventsourcing.commonapi.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String>{


    @Getter private AccountStatus accountStatus;

    public AccountActivatedEvent(String id,  AccountStatus accountStatus) {
        super(id);
        this.accountStatus = accountStatus;
    }
}
