package com.pd.testCredit.feature.loan;


import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/submit")
    public LoanDetails submitApplication(@RequestBody LoanApplication loanApplication) {
        log.debug(String.format("Requested loan application for %s", loanApplication.toString()));
        return loanService.submitApplication(loanApplication);
    }

    @PostMapping("/extend")
    public LoanDetails submitExtension(@RequestBody ExtendApplication extendApplication) {
        log.debug(String.format("Requested loan extension application for %s", extendApplication.toString()));
        return loanService.submitExtension(extendApplication);
    }


}
