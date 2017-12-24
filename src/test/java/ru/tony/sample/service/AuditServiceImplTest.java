package ru.tony.sample.service;

import org.junit.Before;
import org.junit.Test;
import ru.tony.sample.database.repository.AuditRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AuditServiceImplTest {

    private AuditService sut;

    private AuditRepository auditRepository;

    @Before
    public void beforeMethod() {
        auditRepository = mock(AuditRepository.class);
        sut = new AuditServiceImpl(auditRepository);
    }

    @Test
    public void shouldCallCorrectRepositoryMethodForGetAuditRecordList() {
        sut.getAuditRecordList();

        verify(auditRepository, times(1)).findAll();
    }

}