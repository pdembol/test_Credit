package com.pd.testCredit.feature.loan.cotrol;

import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import com.pd.testCredit.feature.loan.validation.ExtendApplicationValidator;
import com.pd.testCredit.feature.loan.validation.LoanApplicationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    private LoanApplicationValidator loanApplicationValidator;

    @Autowired
    private ExtendApplicationValidator extendApplicationValidator;

    @InitBinder("loanApplication")
    public void loanApplicationValidatorBinder(WebDataBinder binder) {
        binder.addValidators(loanApplicationValidator);
    }

    @InitBinder("extendApplication")
    public void extendApplicationValidatorBinder(WebDataBinder binder) {
        binder.addValidators(extendApplicationValidator);
    }


    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/submit")
    public LoanDetails submitApplication(@Valid @RequestBody LoanApplication loanApplication) {
        log.info("Requested loan application accepted for " + loanApplication.toString());
        return loanService.submitApplication(loanApplication);
    }

    @PutMapping("/extend")
    public LoanDetails submitExtension(@Valid @RequestBody ExtendApplication extendApplication) {
        log.info("Requested loan extension application accepted for " + extendApplication.toString());
        return loanService.submitExtension(extendApplication);
    }


}
