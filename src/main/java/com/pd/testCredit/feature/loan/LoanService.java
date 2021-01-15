package com.pd.testCredit.feature.loan;

import com.pd.testCredit.core.validation.CommonValidator;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.UUID;

@Controller
public class LoanService {

    private final LoanRepository loanRepository;
    private final CommonValidator commonValidator;

    public LoanService(LoanRepository loanRepository,
                       CommonValidator commonValidator) {
        this.loanRepository = loanRepository;
        this.commonValidator = commonValidator;
    }

    public LoanDetails submitApplication(LoanApplication loanApplication) {

        commonValidator.validateTime();
        LoanDetails details = LoanMapper.mapAndCalculateLoanDetails(loanApplication);
        loanRepository.insert(details);

        return details;
    }

    public LoanDetails submitExtension(ExtendApplication extendApplication) {

        commonValidator.validateTime();
        LoanDetails details = LoanMapper.mapAndCalculateLoanExtension(
                loanRepository.getOne(extendApplication.getId()),extendApplication);
        loanRepository.update(details);
        return details;
    }

    public Optional<LoanDetails> getLoan(UUID id) {
        return loanRepository.getOne(id);
    }


}
