package com.pd.testCredit.feature.loan.validation;

import com.pd.testCredit.core.validation.CommonValidator;
import com.pd.testCredit.core.validation.ValidatorAdapter;
import com.pd.testCredit.core.utils.MessagesUtils;
import com.pd.testCredit.feature.loan.LoanService;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;

public class ExtendApplicationValidator extends ValidatorAdapter<ExtendApplication> {

    private final CommonValidator commonValidator;
    private final LoanService loanService;


    @Value("${app.loanMaxExtendTime}")
    private Integer loanMaxExtendTime;

    ExtendApplicationValidator(
            CommonValidator commonValidator,
            LoanService loanService
    ){
        this.commonValidator = commonValidator;
        this.loanService = loanService;
    }

    @Override
    public Class<ExtendApplication> isSupported() {
        return ExtendApplication.class;
    }

    @Override
    public void checkValid(ExtendApplication extendApplication, Errors errors) {
        commonValidator.validateMax(
                "loanPeriod",extendApplication.getLoanPeriod(),
                loanMaxExtendTime,
                MessagesUtils.msg("error.loanPeriod.maxExtendTimeExceeded",
                        loanMaxExtendTime),errors);

        if(loanService.getLoan(extendApplication.getId()).isEmpty()){
            errors.rejectValue("id", "form.id.notExist", MessagesUtils.msg("error.notExist",
                    loanMaxExtendTime));
        }
    }

}
