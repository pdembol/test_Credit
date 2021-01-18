package com.pd.testCredit.testObjects;

import com.pd.testCredit.core.utils.MathUtils;
import com.pd.testCredit.feature.loan.entity.LoanDetails;

import java.sql.Date;
import java.time.LocalDate;

public class LoanDetailsObjectsFactory {

    public static LoanDetails getValidNotExtendedLoanApplication(Integer loanPeriod, Integer loanAmount,
                                                                 LocalDate applicationDate) {

        float totalAmount = (float) 107 * loanAmount / 100;
        LocalDate firstInstallmentDate = applicationDate.plusMonths(1);
        LocalDate lastInstallmentDate = firstInstallmentDate.plusMonths(loanPeriod - 1);

        return LoanDetails.builder()
                .name("Jimmy")
                .surname("Tudesky")
                .idNumber("cdp321321321")
                .loanAmount(loanAmount)
                .loanPeriod(loanPeriod)
                .id(UUIDSFactory.uuid1)
                .interest(7)
                .isExtended(false)
                .amountOfInstallment(MathUtils.roundToTwoDecimals(totalAmount / loanPeriod))
                .totalAmount(MathUtils.roundToTwoDecimals(totalAmount))
                .paidAmount((float) 0)
                .remainingInstallments(loanPeriod)
                .dateOfApplication(Date.valueOf(applicationDate))
                .firstInstallmentDate(Date.valueOf(firstInstallmentDate))
                .lastInstallmentDate(Date.valueOf(lastInstallmentDate))
                .build();

    }

    public static LoanDetails getValidExtendedLoanApplication(Integer loanPeriod,
                                                              Integer loanAmount, LocalDate applicationDate) {

        float totalAmount = (float) 107 * loanAmount / 100;
        LocalDate firstInstallmentDate = applicationDate.plusMonths(1);
        LocalDate lastInstallmentDate = firstInstallmentDate.plusMonths(loanPeriod - 1);

        return LoanDetails.builder()
                .name("John")
                .surname("Doe")
                .idNumber("cdp123123123")
                .loanAmount(loanAmount)
                .loanPeriod(loanPeriod)
                .id(UUIDSFactory.uuid2)
                .interest(7)
                .isExtended(true)
                .amountOfInstallment(MathUtils.roundToTwoDecimals(totalAmount / loanPeriod))
                .totalAmount(MathUtils.roundToTwoDecimals(totalAmount))
                .paidAmount((float) 0)
                .remainingInstallments(loanPeriod)
                .dateOfApplication(Date.valueOf(applicationDate))
                .firstInstallmentDate(Date.valueOf(firstInstallmentDate))
                .lastInstallmentDate(Date.valueOf(lastInstallmentDate))
                .build();

    }

}
