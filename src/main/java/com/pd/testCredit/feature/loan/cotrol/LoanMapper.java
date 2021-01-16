package com.pd.testCredit.feature.loan.cotrol;

import com.pd.testCredit.core.exceptions.ApplicationNotExistException;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

@Slf4j
public class LoanMapper {


    private static ZoneId LOCAL_TIME_ZONE = TimeZone.getTimeZone("Europe/Warsaw").toZoneId();

    public static LoanDetails mapAndCalculateLoanDetails(LoanApplication loanApplication) {

        log.debug("Calculating new loan details ...");

        int totalAmount = 107 * loanApplication.getLoanAmount() / 100;
        LocalDate localNow = LocalDate.now(LOCAL_TIME_ZONE);

        // assumed 30 days of waiting, for first installment
        LocalDate firstInstallmentDate = localNow.plusDays(30);

        // assumed paying same day every month
        LocalDate lastInstallmentDate = firstInstallmentDate.plusMonths(loanApplication.getLoanPeriod()-1);

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
                .paidAmount(0)
                .remainingInstallments(loanApplication.getLoanPeriod())
                .dateOfApplication(Date.valueOf(localNow))
                .firstInstallmentDate(Date.valueOf(firstInstallmentDate))
                .lastInstallmentDate(Date.valueOf(lastInstallmentDate))
                .build();
    }


    public static LoanDetails mapAndCalculateLoanExtension(Optional<LoanDetails> loanDetails, ExtendApplication extendApplication) {

        log.debug("Recalculating loan details ...");

        if(loanDetails.isPresent()){
            LoanDetails details = loanDetails.get();
            LocalDate localNow = LocalDate.now(LOCAL_TIME_ZONE);

            // calculating new total period of loan
            int newLoanPeriod = details.getLoanPeriod()+extendApplication.getLoanPeriod();

            //calculating how many installments have been paid off
            LocalDate firstInstallmentDate = details.getFirstInstallmentDate()
                    .toInstant().atZone(LOCAL_TIME_ZONE).toLocalDate();

            Period period = Period.between(firstInstallmentDate, localNow);
            Integer numberOfPaidInstallments = period.getMonths();

            //calculating how much money have been paid off and how much is left to be paid
            Integer paidAmount = details.getTotalAmount() - (numberOfPaidInstallments * details.getAmountOfInstallment());
            Integer amountLeftToPay = details.getTotalAmount() - paidAmount;
            Integer installmentsLeftToPay = details.getLoanPeriod() - numberOfPaidInstallments;
            Integer newInstallmentsNumber = newLoanPeriod - numberOfPaidInstallments;
            Integer newInstallmentAmount = amountLeftToPay / newInstallmentsNumber;

            //calculating new last installment date
            LocalDate newLastInstallmentDate = firstInstallmentDate.plusMonths(extendApplication.getLoanPeriod());

            //setting new loan details
            details.setLoanPeriod(newLoanPeriod);
            details.setIsExtended(true);
            details.setAmountOfInstallment(newInstallmentAmount);
            details.setPaidAmount(paidAmount);
            details.setRemainingInstallments(installmentsLeftToPay);
            details.setLastInstallmentDate(Date.valueOf(newLastInstallmentDate));
            return details;

        } else {
            throw new ApplicationNotExistException(extendApplication.getId());
        }
    }
}
