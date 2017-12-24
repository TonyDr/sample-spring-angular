package ru.tony.sample.rest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.tony.sample.database.entity.Staff;
import ru.tony.sample.database.entity.StaffRole;
import ru.tony.sample.service.StaffService;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.tony.sample.database.entity.StaffRole.ADMIN;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StaffController.class, secure = false)
public class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffService staffService;

    @Captor
    private ArgumentCaptor<Staff> staffArgumentCaptor;

    @Test
    public void shouldReturnCorrectStaffList() throws Exception {
        String staffName = "Petr";
        Staff staff = new Staff();
        staff.setId(2L);
        staff.setName(staffName);
        staff.setRole(ADMIN);
        when(staffService.getStaffList()).thenReturn(Collections.singletonList(staff));

        mockMvc.perform(get("/rest/staff/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name", is(staffName)))
                .andExpect(jsonPath("$[0].role", is(ADMIN.toString()) ));
    }

    @Test
    public void shouldCallCreateStaffWithCorrectParams() throws Exception {

        mockMvc.perform(post("/rest/staff/create")
                        .contentType(APPLICATION_JSON)
                        .content(" { \"name\":\"Ivan\", \"role\":\"ADMIN\"}"))
                .andExpect(status().isOk());

        verify(staffService, times(1)).create(staffArgumentCaptor.capture());
        assertEquals("Ivan", staffArgumentCaptor.getValue().getName());
        assertEquals(ADMIN, staffArgumentCaptor.getValue().getRole());
    }

    @Test
    public void shouldCallUpdateStaffWithCorrectParams() throws Exception {
        mockMvc.perform(post("/rest/staff/update")
                .contentType(APPLICATION_JSON)
                .content(" { \"id\":\"2\",\"name\":\"Ivan\", \"role\":\"ADMIN\"}"))
                .andExpect(status().isOk());

        verify(staffService, times(1)).update(staffArgumentCaptor.capture());
        assertEquals("Ivan", staffArgumentCaptor.getValue().getName());
        assertEquals(ADMIN, staffArgumentCaptor.getValue().getRole());
        assertEquals(2L, staffArgumentCaptor.getValue().getId().longValue());
    }

    @Test
    public void shouldReturnCorrectListOfValues() throws Exception {
        mockMvc.perform(get("/rest/staff/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(StaffRole.values().length)))
                .andExpect(jsonPath("$[0]", is(StaffRole.values()[0].toString())));
    }
}
