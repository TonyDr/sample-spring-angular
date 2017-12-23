package ru.tony.sample.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tony.sample.database.entity.Staff;


public interface StaffRepository extends CrudRepository<Staff, Long> {
}
