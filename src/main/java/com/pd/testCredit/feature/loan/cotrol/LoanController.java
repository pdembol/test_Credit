package com.pd.testCredit.feature.loan.cotrol;

import com.pd.testCredit.core.exceptions.FormErrors;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import com.pd.testCredit.feature.loan.validation.ExtendApplicationValidator;
import com.pd.testCredit.feature.loan.validation.LoanApplicationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;

    private LoanApplicationValidator loanApplicationValidator;

    private ExtendApplicationValidator extendApplicationValidator;

    @InitBinder("loanApplication")
    public void loanApplicationValidatorBinder(WebDataBinder binder) {
        binder.addValidators(loanApplicationValidator);
    }

    @InitBinder("extendApplication")
    public void extendApplicationValidatorBinder(WebDataBinder binder) {
        binder.addValidators(extendApplicationValidator);
    }

    public LoanController(LoanService loanService,
                          LoanApplicationValidator loanApplicationValidator,
                          ExtendApplicationValidator extendApplicationValidator) {
        this.loanService = loanService;
        this.loanApplicationValidator = loanApplicationValidator;
        this.extendApplicationValidator = extendApplicationValidator;
    }

    @PostMapping("/submit")
    public ResponseEntity<LoanDetails> submitApplication(@Valid @RequestBody LoanApplication loanApplication, Errors errors) {
        log.info("Requested loan application accepted for " + loanApplication.toString());
        if (errors.hasErrors()) {
            return new ResponseEntity(new FormErrors(errors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(loanService.submitApplication(loanApplication), HttpStatus.OK);
    }

    @PutMapping("/extend")
    public ResponseEntity<LoanDetails> submitExtension(@Valid @RequestBody ExtendApplication extendApplication, Errors errors) {
        log.info("Requested loan extension application accepted for " + extendApplication.toString());
        if (errors.hasErrors()) {
            return new ResponseEntity(new FormErrors(errors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(loanService.submitExtension(extendApplication), HttpStatus.OK);
    }


}
