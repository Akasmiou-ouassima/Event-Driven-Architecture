package org.sid.comptecqrseventsourcing.query.repositories;

import org.sid.comptecqrseventsourcing.query.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<AccountTransaction,Long> {
}
