package com.pd.testCredit.feature.loan.validation;

import com.pd.testCredit.core.validation.CommonValidator;
import com.pd.testCredit.core.validation.ValidatorAdapter;
import com.pd.testCredit.core.utils.MessagesUtils;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;

public class LoanApplicationValidator extends ValidatorAdapter<LoanDetails> {

    private final CommonValidator commonValidator;

    LoanApplicationValidator(CommonValidator commonValidator){
        this.commonValidator = commonValidator;
    }

    @Value("${app.loanAmount.max}")
    private Integer loanAmountMax;

    @Value("${app.loanAmount.min}")
    private Integer loanAmountMin;

    @Value("${app.loanPeriod.max}")
    private Integer loanPeriodMax;

    @Value("${app.loanPeriod.min}")
    private Integer loanPeriodMin;

    @Override
    public Class<LoanDetails> isSupported() {
        return LoanDetails.class;
    }

    @Override
    public void checkValid(LoanDetails details, Errors errors) {
        commonValidator.validateMax(
                "loanAmount",details.getLoanAmount(),
                loanAmountMax,
                MessagesUtils.msg("error.loanAmount.maxExceeded",
                        loanAmountMax),errors);
        commonValidator.validateMin(
                "loanAmount",details.getLoanAmount(),
                loanAmountMin,
                MessagesUtils.msg("error.loanAmount.minExceeded",
                        loanAmountMin),errors);
        commonValidator.validateMax(
                "loanPeriod",details.getLoanPeriod(),
                loanPeriodMax,
                MessagesUtils.msg("error.loanPeriod.maxExceeded",
                        loanPeriodMax),errors);
        commonValidator.validateMin(
                "loanPeriod",details.getLoanPeriod(),
                loanPeriodMin,
                MessagesUtils.msg("error.loanPeriod.minExceeded",
                        loanPeriodMin),errors);

    }


}
