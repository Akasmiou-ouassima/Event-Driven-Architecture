package org.sid.comptecqrseventsourcing.query.repositories;

import org.sid.comptecqrseventsourcing.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
