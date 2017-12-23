package ru.tony.sample.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.tony.sample.audit.AuditActionType;
import ru.tony.sample.configuration.ActionAuditPostProcessor;
import ru.tony.sample.database.entity.AuditRecord;
import ru.tony.sample.database.entity.Staff;
import ru.tony.sample.database.repository.AuditRepository;
import ru.tony.sample.database.repository.StaffRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static ru.tony.sample.audit.AuditActionType.CREATE_STAFF;
import static ru.tony.sample.audit.AuditActionType.UPDATE_STAFF;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class StaffServiceTest {

    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffRepository repository;

    @Autowired
    private AuditRepository auditRepository;

    @Captor
    private ArgumentCaptor<AuditRecord> auditRecordArgumentCaptor;

    @Before
    public void beforeTest() {
        reset(repository, auditRepository);
        when(repository.save(any(Staff.class))).then(getSaveStaffAnswer());

    }

    @Test
    public void createShouldPassCorrectParamsToSaveMethod() {
        staffService.create(new Staff());

        verify(repository, times(1)).save(any(Staff.class));
        verifyAuditAction(CREATE_STAFF);
    }

    @Test
    public void updateShouldPassCorrectParamsToSaveMethod() {
        staffService.update(new Staff());

        verify(repository, times(1)).save(any(Staff.class));
        verifyAuditAction(UPDATE_STAFF);
    }



    private void verifyAuditAction(AuditActionType updateStaff) {
        verify(auditRepository, times(1)).save(auditRecordArgumentCaptor.capture());
        assertEquals(updateStaff, auditRecordArgumentCaptor.getValue().getType());
    }

    @Configuration
    public static class ContextConfiguration {

        @Bean
        public StaffService staffService() {
            return new StaffServiceImpl(staffRepository());
        }

        @Bean
        public StaffRepository staffRepository() {
            return mock(StaffRepository.class);
        }

        @Bean
        public ActionAuditPostProcessor actionAuditPostProcessor() {
            return new ActionAuditPostProcessor(auditRepository());
        }

        @Bean
        public AuditRepository auditRepository() {
            return mock(AuditRepository.class);
        }

    }

    private Answer<Staff> getSaveStaffAnswer() {
        return new Answer<Staff>() {
            @Override
            public Staff answer(InvocationOnMock invocationOnMock) throws Throwable {
                return (Staff) invocationOnMock.getArguments()[0];
            }
        };
    }
}