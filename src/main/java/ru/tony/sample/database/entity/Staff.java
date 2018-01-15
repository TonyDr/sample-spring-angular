package ru.tony.sample.database.entity;

import ru.tony.sample.audit.AuditedEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Staff implements AuditedEntity,  Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staff_sequence")
    @SequenceGenerator(name="staff_sequence", sequenceName = "staff_sequence")
    private Long id;
    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private StaffRole role;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
