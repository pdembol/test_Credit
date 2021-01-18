package com.pd.testCredit.feature.loan.cotrol;

import com.pd.testCredit.core.exceptions.ApplicationNotExistException;
import com.pd.testCredit.core.utils.MathUtils;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.time.Instant;
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

        float totalAmount = (float) 107 * loanApplication.getLoanAmount() / 100;
        LocalDate localNow = LocalDate.now(LOCAL_TIME_ZONE);

        // assumed 1 month of waiting, for first installment
        LocalDate firstInstallmentDate = localNow.plusMonths(1);

        // assumed paying same day every month
        LocalDate lastInstallmentDate = firstInstallmentDate
                .plusMonths(loanApplication.getLoanPeriod() - 1);

        return LoanDetails.builder()
                .name(loanApplication.getName())
                .surname(loanApplication.getSurname())
                .idNumber(loanApplication.getIdNumber())
                .loanAmount(loanApplication.getLoanAmount())
                .loanPeriod(loanApplication.getLoanPeriod())
                .id(UUID.randomUUID())
                .interest(7)
                .isExtended(false)
                .amountOfInstallment(MathUtils.roundToTwoDecimals(totalAmount / loanApplication.getLoanPeriod()))
                .totalAmount(MathUtils.roundToTwoDecimals(totalAmount))
                .paidAmount((float) 0)
                .remainingInstallments(loanApplication.getLoanPeriod())
                .dateOfApplication(Date.valueOf(localNow))
                .firstInstallmentDate(Date.valueOf(firstInstallmentDate))
                .lastInstallmentDate(Date.valueOf(lastInstallmentDate))
                .build();
    }


    public static LoanDetails mapAndCalculateLoanExtension(Optional<LoanDetails> loanDetails,
                                                           ExtendApplication extendApplication) {

        log.debug("Recalculating loan details ...");

        if (loanDetails.isPresent()) {
            LoanDetails details = loanDetails.get();
            LocalDate localNow = LocalDate.now(LOCAL_TIME_ZONE);

            // calculating new total period of loan
            int newLoanPeriod = details.getLoanPeriod() + extendApplication.getLoanPeriod();

            //calculating how many installments have been paid off
            LocalDate dateOfApplication = Instant.ofEpochMilli(details
                    .getDateOfApplication().getTime()).atZone(LOCAL_TIME_ZONE).toLocalDate();

            LocalDate lastInstallmentDate = Instant.ofEpochMilli(details
                    .getLastInstallmentDate().getTime()).atZone(LOCAL_TIME_ZONE).toLocalDate();

            Period period = Period.between(dateOfApplication, localNow);
            Integer numberOfPaidInstallments = period.getMonths();

            //calculating how much money have been paid off and how much is left to be paid
            Float paidAmount = numberOfPaidInstallments * details.getAmountOfInstallment();
            Float amountLeftToPay = details.getTotalAmount() - paidAmount;
            Integer newInstallmentsNumber = newLoanPeriod - numberOfPaidInstallments;
            Float newInstallmentAmount = amountLeftToPay / newInstallmentsNumber;

            //calculating new last installment date
            LocalDate newLastInstallmentDate = lastInstallmentDate.plusMonths(extendApplication.getLoanPeriod());

            //setting new loan details
            details.setLoanPeriod(newLoanPeriod);
            details.setIsExtended(true);
            details.setAmountOfInstallment(MathUtils.roundToTwoDecimals(newInstallmentAmount));
            details.setPaidAmount(MathUtils.roundToTwoDecimals(paidAmount));
            details.setRemainingInstallments(newInstallmentsNumber);
            details.setLastInstallmentDate(Date.valueOf(newLastInstallmentDate));
            return details;

        } else {
            throw new ApplicationNotExistException(extendApplication.getId());
        }
    }
}
