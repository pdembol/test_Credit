package com.pd.testCredit.feature.loan.cotrol;

import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
        log.info("Requested loan application accepted for " + loanApplication.toString());

        LoanDetails details = loanService.submitApplication(loanApplication);

        return details;
    }

    @PutMapping("/extend")
    public LoanDetails submitExtension(@RequestBody ExtendApplication extendApplication) {
        log.info("Requested loan extension application accepted for " + extendApplication.toString());
        return loanService.submitExtension(extendApplication);
    }


}
