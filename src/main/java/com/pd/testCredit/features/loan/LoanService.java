package com.pd.testCredit.features.loan;


import com.pd.testCredit.data.model.ExtendApplication;
import com.pd.testCredit.data.model.LoanApplication;
import com.pd.testCredit.data.model.LoanDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/loan")
public class LoanService  {

    private final LoanController loanController;

    public LoanService(LoanController loanController) {
        this.loanController = loanController;
    }

    @PostMapping("/submit")
    public LoanDetails submitApplication(@RequestBody LoanApplication loanApplication) {
        log.debug(String.format("Requested loan application for %s", loanApplication.toString()));
        return loanController.submitApplication(loanApplication);
    }

    @PostMapping("/extend")
    public LoanDetails submitExtension(@RequestBody ExtendApplication extendApplication) {
        log.debug(String.format("Requested loan extension application for %s", extendApplication.toString()));
        return loanController.submitExtension(extendApplication);
    }


}
