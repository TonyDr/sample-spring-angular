package ru.tony.sample.service;

import ru.tony.sample.database.entity.AuditRecord;

import java.util.List;

public interface AuditService {

    List<AuditRecord> getAuditRecordList();
}
