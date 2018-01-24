package ru.tony.sample.database.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.tony.sample.database.DBConnectionConfiguration;
import ru.tony.sample.database.entity.Staff;
import ru.tony.sample.database.entity.StaffRole;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
// TODO annotation do not wok
@DatabaseSetup("datasets/staff.xml")
public class StaffRepositoryTest {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void shouldCorrectCreateStaffUser() {
        Staff staff = staffRepository.save(new Staff());
        assertNotNull(staff.getId());
    }

    @Test
    public void shouldCorrectFindStaffByName() {
        Staff staff = staffRepository.findByName("USER");
        assertEquals(StaffRole.USER, staff.getRole());
    }
}