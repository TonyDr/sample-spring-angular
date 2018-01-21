package ru.tony.sample.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= MOCK)
@AutoConfigureMockMvc
public class SecurityTest {


    @Autowired
    private MockMvc mvc;

    @Test
    public void requestShouldFailWithNotAuthorized() throws Exception {
        mvc.perform(get("/api/staff/list"))
                .andExpect(status().is(401));

        mvc.perform(get("/api/audit/list"))
                .andExpect(status().is(401));
    }
}
