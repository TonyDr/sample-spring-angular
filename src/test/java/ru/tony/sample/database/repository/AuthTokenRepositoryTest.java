package ru.tony.sample.database.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tony.sample.database.entity.AuthToken;
import ru.tony.sample.database.entity.Staff;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@DatabaseSetup("datasets/staff.xml")
public class AuthTokenRepositoryTest {


    @Autowired
    private StaffRepository repository;

    @Autowired
    private AuthTokenRepository tokenRepository;

    @Test
    public void shouldCorrectSaveAuthToken() {
        Staff staff = repository.getOne(1L);

        AuthToken token = new AuthToken();
        token.setToken("testToken");
        token.setExpireTime(new Date());
        token.setStaff(staff);

        token = tokenRepository.save(token);

        token = tokenRepository.findOne(token.getId());
        assertNotNull(token);
    }
}