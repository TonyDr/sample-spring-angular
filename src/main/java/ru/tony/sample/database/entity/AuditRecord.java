package ru.tony.sample.database.entity;

import ru.tony.sample.audit.AuditActionType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class AuditRecord {

    @Id
    @GeneratedValue
    private Long id;

    private AuditActionType type;
    private Long recordId;
    private String objectName;
    private Timestamp time;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getTime() {
        return time;
    }
}
