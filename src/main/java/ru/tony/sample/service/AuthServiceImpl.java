package ru.tony.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tony.sample.audit.AuditAction;
import ru.tony.sample.audit.AuditActionType;
import ru.tony.sample.audit.AuditedClass;
import ru.tony.sample.database.entity.AuthToken;
import ru.tony.sample.database.entity.Staff;
import ru.tony.sample.database.repository.AuthTokenRepository;
import ru.tony.sample.database.repository.StaffRepository;
import ru.tony.sample.service.exception.IncorrectPasswordException;

import java.util.Date;
import java.util.UUID;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

@Service
@AuditedClass
public class AuthServiceImpl implements AuthService {

    private StaffRepository staffRepository;
    private AuthTokenRepository authTokenRepository;

    @Autowired
    public AuthServiceImpl(StaffRepository staffRepository, AuthTokenRepository authTokenRepository) {
        this.staffRepository = staffRepository;
        this.authTokenRepository = authTokenRepository;
    }

    @Override
    @AuditAction(type = AuditActionType.LOGIN)
    public String authorize(String login, String password) {
        Staff staff = staffRepository.findByNameAndPassword(login, password);
        checkStaffFound(staff);

        return createAndSafeToken(staff);
    }

    private void checkStaffFound(Staff staff) {
        if (staff == null) {
            throw new IncorrectPasswordException();
        }
    }

    private String createAndSafeToken(Staff staff) {
        String token = sha1Hex(staff.getName()) + sha1Hex(staff.getRole().toString()) + UUID.randomUUID().toString();
        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setStaff(staff);
        authToken.setExpireTime(new Date(System.currentTimeMillis() + 36000));
        authTokenRepository.save(authToken);
        return token;
    }
}
