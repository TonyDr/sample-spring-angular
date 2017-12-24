package ru.tony.sample.database.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.tony.sample.database.DBConnectionConfiguration;
import ru.tony.sample.database.entity.Staff;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DBConnectionConfiguration.class})
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
}