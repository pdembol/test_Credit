package com.pd.testCredit.feature.loan.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import com.pd.testCredit.testObjects.ExtendApplicationObjectsFactory;
import com.pd.testCredit.testObjects.LoanApplicationObjectsFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.ZoneId;
import java.util.TimeZone;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerIntegrationTest {

    private static ZoneId LOCAL_TIME_ZONE = TimeZone.getTimeZone("Europe/Warsaw").toZoneId();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();


    @Test
    public void shouldAcceptApplicationAndReturnDetails() throws Exception {
       LoanApplication application =  LoanApplicationObjectsFactory
               .getValidLoanApplication(12,50000);

        mockMvc.perform(post("/loan/submit")
                .content(mapper.writeValueAsString(application))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'Jimmy'}"))
                .andExpect(content().json("{'surname':'Tudesky'}"))
                .andExpect(content().json("{'loanPeriod':12}"))
                .andExpect(content().json("{'loanAmount':50000}"));

    }

    @Test
    public void shouldAcceptApplicationThenReturnDetailsThenExtendAndReturnNewDetails() throws Exception {
        LoanApplication application =  LoanApplicationObjectsFactory
                .getValidLoanApplication(10,20000);

        MvcResult result = mockMvc.perform(post("/loan/submit")
                            .content(mapper.writeValueAsString(application))
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andExpect(content().json("{'name':'Jimmy'}"))
                            .andExpect(content().json("{'surname':'Tudesky'}"))
                            .andExpect(content().json("{'loanPeriod':10}"))
                            .andExpect(content().json("{'loanAmount':20000}"))
                            .andReturn();


        LoanDetails details = mapper.readValue(result.getResponse().getContentAsByteArray(), LoanDetails.class);

        ExtendApplication extendApplication = ExtendApplicationObjectsFactory.getValidTestExtendApplication(4);
        extendApplication.setId(details.getId());

        mockMvc.perform(put("/loan/extend")
                .content(mapper.writeValueAsString(extendApplication))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'Jimmy'}"))
                .andExpect(content().json("{'surname':'Tudesky'}"))
                .andExpect(content().json("{'loanPeriod':14}"))
                .andExpect(content().json("{'loanAmount':20000}"));
    }


    @Test
    public void shouldCallComponentsSubmitApplicationMethodAndGetBadRequest() throws Exception {
        ExtendApplication extendApplication = new ExtendApplication(UUID.randomUUID(),1000);
        mockMvc.perform(put("/loan/extend")
                .content(mapper.writeValueAsString(extendApplication))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}
