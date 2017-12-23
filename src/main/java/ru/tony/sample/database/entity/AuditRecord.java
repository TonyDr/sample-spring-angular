package ru.tony.sample.database.entity;

import ru.tony.sample.audit.AuditActionType;

public class AuditRecord {
    private AuditActionType type;
    private Long recordId;
    private String objectName;

    public void setType(AuditActionType type) {
        this.type = type;
    }

    public AuditActionType getType() {
        return type;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectName() {
        return objectName;
    }
}
