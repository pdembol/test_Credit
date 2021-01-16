package com.pd.testCredit.testObjects;

import com.pd.testCredit.feature.loan.entity.LoanDetails;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

public class LoanDetailsObjectsFactory {

    public static LoanDetails getValidLoanApplication(Integer loanPeriod, Integer loanAmount, LocalDate applicationDate) {

        int totalAmount = 107 * loanAmount / 100;
        LocalDate firstInstallmentDate = applicationDate.plusDays(30);
        LocalDate lastInstallmentDate = firstInstallmentDate.plusMonths(loanPeriod-1);

        return LoanDetails.builder()
            .name("Jimmy")
            .surname("Tudesky")
            .idNumber("cdp321321321")
            .loanAmount(loanAmount)
            .loanPeriod(loanPeriod)
            .id(UUIDSFactory.uuid1)
            .interest(7)
            .isExtended(false)
            .amountOfInstallment(totalAmount/loanPeriod)
            .totalAmount(totalAmount)
            .paidAmount(0)
            .remainingInstallments(loanPeriod)
            .dateOfApplication(Date.valueOf(applicationDate))
            .firstInstallmentDate(Date.valueOf(firstInstallmentDate))
            .lastInstallmentDate(Date.valueOf(lastInstallmentDate))
            .build();

    }

}
