package ru.tony.sample.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tony.sample.database.entity.Staff;
import ru.tony.sample.database.entity.StaffRole;
import ru.tony.sample.service.StaffService;

import java.util.List;

@RestController
@RequestMapping("/rest/staff")
public class StaffController {


    private final StaffService staffService;

    @Autowired
    StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/list")
    public List<Staff> getStaffList() {
        return staffService.getStaffList();
    }

    @PostMapping("/create")
    public void createStaff(@RequestBody Staff staff) {
        staffService.create(staff);
    }

    @PostMapping("/update")
    public void updateStaff(@RequestBody Staff staff) {
        staffService.update(staff);
    }

    @GetMapping("/roles")
    public StaffRole [] getStaffRoles() {
        return StaffRole.values();
    }
}
