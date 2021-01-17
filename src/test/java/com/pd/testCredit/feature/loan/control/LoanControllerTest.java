package com.pd.testCredit.feature.loan.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pd.testCredit.feature.loan.cotrol.LoanController;
import com.pd.testCredit.feature.loan.cotrol.LoanService;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import com.pd.testCredit.testObjects.LoanApplicationObjectsFactory;
import com.pd.testCredit.testObjects.LoanDetailsObjectsFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimeZone;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoanController.class)
public class LoanControllerTest {

    private static ZoneId LOCAL_TIME_ZONE = TimeZone.getTimeZone("Europe/Warsaw").toZoneId();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LoanService loanService;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();


    @Test
    public void should_return_created_loan_details() throws Exception {
       LoanApplication application =  LoanApplicationObjectsFactory.getValidLoanApplication(12,50000);

        mockMvc.perform(post("/loan/submit")
                .content(mapper.writeValueAsString(application))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_create_and_extend_created_loan() throws Exception {

        LocalDate localNow = LocalDate.now(LOCAL_TIME_ZONE);

        LoanApplication application =  LoanApplicationObjectsFactory.getValidLoanApplication(12,50000);
        LoanDetails details = LoanDetailsObjectsFactory.getValidNotExtendedLoanApplication(12,50000,localNow);
        when(loanService.submitApplication(application)).thenReturn(details);

        ExtendApplication extendApplication =  new ExtendApplication(details.getId(),4);

        mockMvc.perform(put("/loan/extend")
                .content(mapper.writeValueAsString(application))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
