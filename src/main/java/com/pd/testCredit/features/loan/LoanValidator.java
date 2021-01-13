package com.pd.testCredit.features.loan;

import com.pd.testCredit.data.model.ExtendApplication;
import com.pd.testCredit.data.model.LoanApplication;
import org.springframework.stereotype.Service;

@Service
public class LoanValidator {

    private LoanRepository loanRepository;

    public LoanValidator(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public void validateApplication(LoanApplication loanApplication) {
    }

    public void validateExtension(ExtendApplication extendApplication) {
    }
}
