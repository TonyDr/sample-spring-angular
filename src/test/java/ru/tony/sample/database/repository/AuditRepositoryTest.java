package ru.tony.sample.database.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.tony.sample.database.DBConnectionConfiguration;
import ru.tony.sample.database.entity.AuditRecord;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DBConnectionConfiguration.class})
public class AuditRepositoryTest {

    @Autowired
    private AuditRepository auditRepository;

    @Test
    public void createAuditRecordShouldSuccess() {
        AuditRecord record = auditRepository.save(new AuditRecord());
        assertNotNull(record.getId());
    }

}