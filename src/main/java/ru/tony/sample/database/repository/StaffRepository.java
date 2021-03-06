package ru.tony.sample.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tony.sample.database.entity.Staff;


public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByNameAndPassword(String login, String password);

    Staff findByName(String name);
}
