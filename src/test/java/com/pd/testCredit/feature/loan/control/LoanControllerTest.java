package com.pd.testCredit.feature.loan.control;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pd.testCredit.feature.loan.cotrol.LoanController;
import com.pd.testCredit.feature.loan.cotrol.LoanService;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.testObjects.LoanApplicationObjectsFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoanController.class)
public class LoanControllerTest {

    @MockBean
    LoanService loanService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_created_loan_details() throws Exception {
       LoanApplication application =  LoanApplicationObjectsFactory.getValidLoanApplication(12,50000);

        mockMvc.perform(post("/loan/submit")
                .content(mapper.writeValueAsString(application))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
