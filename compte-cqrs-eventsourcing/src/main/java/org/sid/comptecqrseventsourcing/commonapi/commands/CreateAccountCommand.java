package org.sid.comptecqrseventsourcing.commonapi.commands;


import lombok.Getter;

public class CreateAccountCommand extends BaseCommand<String>{

    @Getter private double initialeBalance;
    @Getter private  String currency;
    public CreateAccountCommand(String id, double initialeBalance, String currency) {
        super(id);
        this.initialeBalance = initialeBalance;
        this.currency = currency;
    }
}
