package com.pd.testCredit.feature.loan.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import com.pd.testCredit.testObjects.ExtendApplicationObjectsFactory;
import com.pd.testCredit.testObjects.LoanApplicationObjectsFactory;
import com.pd.testCredit.testObjects.UUIDSFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "")
public class LoanControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @Value("${app.loanAmount.max}")
    private Integer loanAmountMax;

    @Value("${app.loanPeriod.max}")
    private Integer loanPeriodMax;

    @Value("${app.loanMaxExtendTime}")
    private Integer loanMaxExtendTime;


    @Test
    public void shouldAcceptApplicationAndReturnDetails() throws Exception {
        LoanApplication application = LoanApplicationObjectsFactory
                .getValidLoanApplication(loanPeriodMax, loanAmountMax);

        mockMvc.perform(post("/loan/submit")
                .content(mapper.writeValueAsString(application))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'Jimmy'}"))
                .andExpect(content().json("{'surname':'Tudesky'}"))
                .andExpect(content().json("{'loanPeriod':" + loanPeriodMax + "}"))
                .andExpect(content().json("{'loanAmount':" + loanAmountMax + "}"))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    public void shouldAcceptApplicationThenReturnDetailsThenExtendAndReturnNewDetails() throws Exception {
        LoanApplication application = LoanApplicationObjectsFactory
                .getValidLoanApplication(loanPeriodMax, loanAmountMax);

        MvcResult result = mockMvc.perform(post("/loan/submit")
                .content(mapper.writeValueAsString(application))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'Jimmy'}"))
                .andExpect(content().json("{'surname':'Tudesky'}"))
                .andExpect(content().json("{'loanPeriod':" + loanPeriodMax + "}"))
                .andExpect(content().json("{'loanAmount':" + loanAmountMax + "}"))
                .andReturn();


        LoanDetails details = mapper.readValue(result.getResponse().getContentAsByteArray(), LoanDetails.class);

        ExtendApplication extendApplication = ExtendApplicationObjectsFactory.getValidTestExtendApplication(loanMaxExtendTime);
        extendApplication.setId(details.getId());

        Integer newLoanPeriod = loanMaxExtendTime + loanPeriodMax;

        mockMvc.perform(put("/loan/extend")
                .content(mapper.writeValueAsString(extendApplication))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'Jimmy'}"))
                .andExpect(content().json("{'surname':'Tudesky'}"))
                .andExpect(content().json("{'loanPeriod':" + newLoanPeriod + "}"))
                .andExpect(content().json("{'loanAmount':" + loanAmountMax + "}"))
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    public void shouldRejectExtendApplicationBecauseOfTooLongLoanPeriodAndIdNotExist() throws Exception {
        ExtendApplication extendApplication = new ExtendApplication(UUIDSFactory.uuid1, loanMaxExtendTime + 10);
        mockMvc.perform(put("/loan/extend")
                .content(mapper.writeValueAsString(extendApplication))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'errors':[{\"field\":\"loanPeriod\",\"message\":\"Requested time to extend the loan repayment is too high. The longest possible loan extension period is " + loanMaxExtendTime + "\"},{\"field\":\"id\",\"message\":\"Application with id " + UUIDSFactory.uuid1 + " not exist\"}]}"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void shouldRejectExtendApplicationBecauseOfNullIdInForm() throws Exception {
        ExtendApplication extendApplication = new ExtendApplication(null, loanMaxExtendTime);
        mockMvc.perform(put("/loan/extend")
                .content(mapper.writeValueAsString(extendApplication))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\":[{\"field\":\"id\",\"message\":\"Field is required\"}]}"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void shouldRejectApplicationBecauseOfTooLongLoanPeriodAndTooHighLoanAmount() throws Exception {
        LoanApplication application = LoanApplicationObjectsFactory
                .getValidLoanApplication(loanPeriodMax + 10, loanAmountMax + 10);

        mockMvc.perform(post("/loan/submit")
                .content(mapper.writeValueAsString(application))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\":[{\"field\":\"loanAmount\",\"message\":\"Requested loan amount is too high. The highest possible loan amount is " + loanAmountMax + "\"},{\"field\":\"loanPeriod\",\"message\":\"Requested loan period is too long. The longest possible loan period is " + loanPeriodMax + "\"}]}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void shouldRejectApplicationBecauseOfNullsInNotValidatedByCustomValidatorsFields() throws Exception {
        LoanApplication application = LoanApplicationObjectsFactory
                .getValidLoanApplication(loanPeriodMax, loanAmountMax);

        application.setName(null);
        application.setIdNumber(null);

        mockMvc.perform(post("/loan/submit")
                .content(mapper.writeValueAsString(application))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"errors\":[{\"field\":\"idNumber\",\"message\":\"must not be null\"},{\"field\":\"name\",\"message\":\"must not be null\"}]}"))
                .andDo(MockMvcResultHandlers.print());

    }


}
