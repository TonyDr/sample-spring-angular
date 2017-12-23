package ru.tony.sample.database.entity;

import ru.tony.sample.audit.AuditedEntity;

import java.io.Serializable;

public class Staff implements AuditedEntity,  Serializable{

    private String name;
    private StaffRole role;
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(StaffRole role) {
        this.role = role;
    }

    public StaffRole getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
