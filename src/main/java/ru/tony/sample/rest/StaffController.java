package ru.tony.sample.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tony.sample.database.entity.Staff;
import ru.tony.sample.service.StaffServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/rest/staff")
public class StaffController {


    private final StaffServiceImpl staffService;

    @Autowired
    StaffController(StaffServiceImpl staffService) {
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
}
