package ru.tony.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tony.sample.audit.AuditAction;
import ru.tony.sample.audit.AuditedClass;
import ru.tony.sample.database.entity.Staff;
import ru.tony.sample.database.repository.StaffRepository;

import java.util.List;

import static ru.tony.sample.audit.AuditActionType.CREATE_STAFF;
import static ru.tony.sample.audit.AuditActionType.UPDATE_STAFF;

@Service
@AuditedClass
public class StaffServiceImpl implements StaffService {

    private final StaffRepository repository;

    @Autowired
    public StaffServiceImpl(StaffRepository repository) {
        this.repository = repository;
    }

    public List<Staff> getStaffList() {
        return null;
    }

    @Override
    @AuditAction(type = CREATE_STAFF)
    public Staff create(Staff staff) {
        return repository.save(staff);
    }

    @Override
    @AuditAction(type = UPDATE_STAFF)
    public Staff update(Staff staff) {
        return repository.save(staff);
    }
}
