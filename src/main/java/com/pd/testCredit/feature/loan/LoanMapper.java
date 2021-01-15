package com.pd.testCredit.feature.loan;

import com.pd.testCredit.core.exceptions.ApplicationNotExistException;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

public class LoanMapper {

    public static LoanDetails mapAndCalculateLoanDetails(LoanApplication loanApplication) {

        int totalAmount = 107 * loanApplication.getLoanAmount() / 100;
        LocalDateTime localNow = LocalDateTime.now(TimeZone.getTimeZone("Europe/Warsaw").toZoneId());

        return LoanDetails.builder()
                .name(loanApplication.getName())
                .surname(loanApplication.getSurname())
                .idNumber(loanApplication.getIdNumber())
                .loanAmount(loanApplication.getLoanAmount())
                .loanPeriod(loanApplication.getLoanPeriod())
                .id(UUID.randomUUID())
                .interest(7)
                .isExtended(false)
                .amountOfInstallment(totalAmount/loanApplication.getLoanPeriod())
                .totalAmount(totalAmount)
                .dateOfApplication(Timestamp.valueOf(localNow))
                .firstInstallmentDate(Timestamp.valueOf(localNow))
                .lastInstallmentDate(Timestamp.valueOf(localNow))
                .build();
    }


    public static LoanDetails mapAndCalculateLoanExtension(Optional<LoanDetails> loanDetails, ExtendApplication extendApplication) {
        if(loanDetails.isPresent()){
            LoanDetails details = loanDetails.get();
            int newLoanPeriod = details.getLoanPeriod()+extendApplication.getLoanPeriod();

            details.setIsExtended(true);
            details.setLoanPeriod(newLoanPeriod);
            details.setAmountOfInstallment(details.getTotalAmount()/newLoanPeriod);

            return details;

        } else {
            throw new ApplicationNotExistException(extendApplication.getId());
        }
    }
}
