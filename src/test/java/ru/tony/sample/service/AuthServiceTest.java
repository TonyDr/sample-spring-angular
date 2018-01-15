package ru.tony.sample.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.tony.sample.audit.AuditActionType;
import ru.tony.sample.configuration.ActionAuditPostProcessor;
import ru.tony.sample.database.entity.AuditRecord;
import ru.tony.sample.database.entity.AuthToken;
import ru.tony.sample.database.entity.Staff;
import ru.tony.sample.database.repository.AuditRepository;
import ru.tony.sample.database.repository.AuthTokenRepository;
import ru.tony.sample.database.repository.StaffRepository;
import ru.tony.sample.service.exception.IncorrectPasswordException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static ru.tony.sample.database.entity.StaffRole.ADMIN;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AuthServiceTest {

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    @Autowired
    private AuthService authService;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private AuthTokenRepository tokenRepository;

    @Captor
    private ArgumentCaptor<AuditRecord> auditRecordArgumentCaptor;

    @Captor
    private ArgumentCaptor<AuthToken> authTokenArgumentCaptor;

    @Before
    public void beforeTest() {
        reset(staffRepository, tokenRepository, auditRepository);
    }


    @Test(expected = IncorrectPasswordException.class)
    public void shouldReturnErrorWhenIncorrectCredentials() {
        authService.authorize(USER, PASSWORD);
    }

    @Test
    public void shouldCorrectAuthorizeStaff() {

        when(staffRepository.findByNameAndPassword(eq(USER), eq(PASSWORD))).thenReturn(getStaff());

        String result = authService.authorize(USER, PASSWORD);

        assertNotNull(result);
        verify(staffRepository).findByNameAndPassword(eq(USER), eq(PASSWORD));
        verify(tokenRepository).save(authTokenArgumentCaptor.capture());
        assertEquals(authTokenArgumentCaptor.getValue().getToken(), result);
        verifyAuditAction(AuditActionType.LOGIN);
    }

    private void verifyAuditAction(AuditActionType updateStaff) {
        verify(auditRepository, times(1)).save(auditRecordArgumentCaptor.capture());
        assertEquals(updateStaff, auditRecordArgumentCaptor.getValue().getType());
    }


    private Staff getStaff() {
        Staff staff = new Staff();
        staff.setId(1L);
        staff.setName("name");
        staff.setPassword("password");
        staff.setRole(ADMIN);
        return staff;
    }

    @Configuration
    public static class ContextConfiguration {

        @Bean
        public AuthService authService() {
            return new AuthServiceImpl(staffRepository(), authTokenRepository());
        }

        @Bean
        public StaffRepository staffRepository() {
            return mock(StaffRepository.class);
        }

        @Bean
        public AuthTokenRepository authTokenRepository() {
            return mock(AuthTokenRepository.class);
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

}