package ru.tony.sample.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= MOCK)
@AutoConfigureMockMvc
public class SecurityTest {


    @Autowired
    private MockMvc mvc;

    @Test
    public void requestShouldFailWithNotAuthorized() throws Exception {
        mvc.perform(get("/rest/staff/list"))
                .andExpect(status().is(401));

        mvc.perform(get("/rest/audit/list"))
                .andExpect(status().is(401));
    }

    @Test
    public void requestShouldFailForIncorrectTokenInHeader() throws Exception {
        mvc.perform(get("/rest/staff/list").header("auth-token", "incorrect-token"))
                .andExpect(status().is(401));

        mvc.perform(get("/rest/audit/list").header("auth-token", "incorrect-token"))
                .andExpect(status().is(401));
    }


    @Test
    public void requestShouldPassCorrectForAuthorizedAdmin() throws Exception {
        String token = mvc.perform(post("/login"). contentType(APPLICATION_JSON)
                .content("{\"login\":\"ADMIN\", \"password\":\"ADMIN_PASS\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        mvc.perform(get("/rest/staff/list").header("auth-token", token))
                .andExpect(status().is(200));

        mvc.perform(get("/rest/audit/list").header("auth-token", token))
                .andExpect(status().is(200));
    }

    @Test
    public void requestShouldPassCorrectForAuthorizedUser() throws Exception {
        String token = mvc.perform(post("/login"). contentType(APPLICATION_JSON)
                .content("{\"login\":\"USER\", \"password\":\"USER_PASS\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        mvc.perform(get("/rest/staff/list").header("auth-token", token))
                .andExpect(status().is(403));

        mvc.perform(get("/rest/audit/list").header("auth-token", token))
                .andExpect(status().is(200));
    }
}
