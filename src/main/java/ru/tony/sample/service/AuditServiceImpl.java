package ru.tony.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tony.sample.database.entity.AuditRecord;
import ru.tony.sample.database.repository.AuditRepository;

import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Autowired
    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public List<AuditRecord> getAuditRecordList() {
        return auditRepository.findAll();
    }
}
