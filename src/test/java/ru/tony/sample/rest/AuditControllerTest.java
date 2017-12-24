package ru.tony.sample.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.tony.sample.database.entity.AuditRecord;
import ru.tony.sample.service.AuditService;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.tony.sample.audit.AuditActionType.CREATE_STAFF;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AuditController.class, secure = false)
public class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditService auditService;


    @Test
    public void shouldReturnCorrectCountOfAuditRecord() throws Exception {
        AuditRecord auditRecord = new AuditRecord();
        auditRecord.setId(2L);
        auditRecord.setType(CREATE_STAFF);
        auditRecord.setObjectName("Staff");
        when(auditService.getAuditRecordList()).thenReturn(singletonList(auditRecord));

        mockMvc.perform(get("/rest/audit/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].objectName", is("Staff")))
                .andExpect(jsonPath("$[0].type", is(CREATE_STAFF.toString()) ));
    }
}
