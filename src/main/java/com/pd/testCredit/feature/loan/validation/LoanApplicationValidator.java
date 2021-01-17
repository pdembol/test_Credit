package com.pd.testCredit.feature.loan.validation;

import com.pd.testCredit.core.validation.CommonValidator;
import com.pd.testCredit.core.validation.ValidatorAdapter;
import com.pd.testCredit.core.utils.MessagesUtils;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Component which validates LoanApplication fields
 * Rules :
 *  - request received at night hours ( 0:00 - 6:00 ) with maximum allowed loan amount should be rejected
 *  - amount of loan must be between maximum and minimum , given in properties
 *  - period of loan must be between maximum and minimum , given in properties
 */
@Slf4j
@Component
public class LoanApplicationValidator extends ValidatorAdapter<LoanApplication> {

    @Value("${app.loanAmount.max}")
    private Integer loanAmountMax;

    @Value("${app.loanAmount.min}")
    private Integer loanAmountMin;

    @Value("${app.loanPeriod.max}")
    private Integer loanPeriodMax;

    @Value("${app.loanPeriod.min}")
    private Integer loanPeriodMin;

    private CommonValidator commonValidator;

    public LoanApplicationValidator(CommonValidator commonValidator){
        this.commonValidator = commonValidator;
    }

    @Override
    public Class<LoanApplication> isSupported() {
        return LoanApplication.class;
    }

    @Override
    public void checkValid(LoanApplication application, Errors errors) {

        log.info("Validating loan application ...");

        commonValidator.validateTimeAndAmount(
                "loanAmount",application.getLoanAmount(),
                loanAmountMax,
                MessagesUtils.msg("error.tooLateAndTooMuch"),errors);

        CommonValidator.validateMax(
                "loanAmount",application.getLoanAmount(),
                loanAmountMax,
                MessagesUtils.msg("error.loanAmount.maxExceeded",
                        loanAmountMax),errors);

        CommonValidator.validateMin(
                "loanAmount",application.getLoanAmount(),
                loanAmountMin,
                MessagesUtils.msg("error.loanAmount.minExceeded",
                        loanAmountMin),errors);

        CommonValidator.validateMax(
                "loanPeriod",application.getLoanPeriod(),
                loanPeriodMax,
                MessagesUtils.msg("error.loanPeriod.maxExceeded",
                        loanPeriodMax),errors);

        CommonValidator.validateMin(
                "loanPeriod",application.getLoanPeriod(),
                loanPeriodMin,
                MessagesUtils.msg("error.loanPeriod.minExceeded",
                        loanPeriodMin),errors);

    }


}
