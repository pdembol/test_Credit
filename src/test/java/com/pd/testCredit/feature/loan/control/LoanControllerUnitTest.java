package com.pd.testCredit.feature.loan.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pd.testCredit.feature.loan.cotrol.LoanController;
import com.pd.testCredit.feature.loan.cotrol.LoanRepository;
import com.pd.testCredit.feature.loan.cotrol.LoanService;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.testObjects.LoanApplicationObjectsFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoanController.class)
public class LoanControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Autowired
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldCallComponentsSubmitApplicationMethod() throws Exception {
       LoanApplication application =  LoanApplicationObjectsFactory
               .getValidLoanApplication(12,50000);

        mockMvc.perform(post("/loan/submit")
                .content(mapper.writeValueAsString(application))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCallComponentsSubmitExtensionMethod() throws Exception {
        ExtendApplication extendApplication = new ExtendApplication(UUID.randomUUID(),4);

        mockMvc.perform(put("/loan/extend")
                .content(mapper.writeValueAsString(extendApplication))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
