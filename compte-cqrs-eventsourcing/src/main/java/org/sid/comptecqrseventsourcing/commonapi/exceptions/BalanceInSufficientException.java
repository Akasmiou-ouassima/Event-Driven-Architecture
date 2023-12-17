package org.sid.comptecqrseventsourcing.commonapi.exceptions;

public class BalanceInSufficientException extends RuntimeException {
    public BalanceInSufficientException(String message) {
        super(message);
    }
}
