package ru.tony.sample.service;

import ru.tony.sample.database.entity.Staff;

import java.util.List;

public interface StaffService {

    List<Staff> getStaffList();

    Staff create(Staff staff);

    Staff update(Staff staff);
}
