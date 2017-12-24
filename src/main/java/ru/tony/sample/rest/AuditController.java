package ru.tony.sample.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tony.sample.database.entity.AuditRecord;
import ru.tony.sample.service.AuditService;

import java.util.List;

@RestController
@RequestMapping("/rest/audit")
public class AuditController {

    private final AuditService auditService;

    AuditController(AuditService auditService) {
        this.auditService = auditService;
    }


    @GetMapping("/list")
    public List<AuditRecord> getAuditRecordList() {
        return auditService.getAuditRecordList();
    }
}
