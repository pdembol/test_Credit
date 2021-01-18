package com.pd.testCredit.feature.loan.cotrol;

import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public LoanDetails submitApplication(LoanApplication loanApplication) {

        log.info("Loan details calculating");

        LoanDetails details = LoanMapper.mapAndCalculateLoanDetails(loanApplication);

        log.info("Loan details saving");

        loanRepository.insert(details);

        return details;
    }

    public LoanDetails submitExtension(ExtendApplication extendApplication) {

        log.info("Loan details with extension calculating");

        LoanDetails details = LoanMapper.mapAndCalculateLoanExtension(
                loanRepository.getOne(extendApplication.getId()), extendApplication);

        log.info("New loan details saving");

        loanRepository.update(details);

        return details;
    }

    public Optional<LoanDetails> getLoan(UUID id) {
        return loanRepository.getOne(id);
    }


}
