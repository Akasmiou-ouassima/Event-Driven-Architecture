package org.sid.comptecqrseventsourcing.commonapi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseCommand <T> {
    @TargetAggregateIdentifier // identifiant de l'agrégat cible concerné par la commande c'est le compte
    @Getter private T id;

    public BaseCommand(T id) {
        this.id = id;
    }
}
