package com.pd.testCredit.features.loan;

import com.pd.testCredit.data.model.ExtendApplication;
import com.pd.testCredit.data.model.LoanApplication;
import com.pd.testCredit.data.model.LoanDetails;
import org.springframework.stereotype.Controller;

@Controller
public class LoanController {

    private LoanRepository loanRepository;
    private final LoanValidator loanValidator;


    public LoanController(LoanRepository loanRepository,
                          LoanValidator loanValidator) {
        this.loanRepository = loanRepository;
        this.loanValidator = loanValidator;
    }

    public LoanDetails submitApplication(LoanApplication loanApplication) {
        loanValidator.validateApplication(loanApplication);

        // calculate details;
        LoanDetails details = new LoanDetails();

        loanRepository.insert(details);

        return details;
    }

    public LoanDetails submitExtension(ExtendApplication extendApplication) {
        loanValidator.validateExtension(extendApplication);

        // get details;
        LoanDetails details = loanRepository.getOne(extendApplication.getId());

        // update details;

        return details;
    }


}
