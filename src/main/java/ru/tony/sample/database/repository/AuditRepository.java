package ru.tony.sample.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tony.sample.database.entity.AuditRecord;

public interface AuditRepository extends JpaRepository<AuditRecord, Long> {
}
