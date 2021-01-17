package com.pd.testCredit.feature.loan.validation;

import com.pd.testCredit.core.validation.CommonValidator;
import com.pd.testCredit.core.validation.ValidatorAdapter;
import com.pd.testCredit.core.utils.MessagesUtils;
import com.pd.testCredit.feature.loan.cotrol.LoanService;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import java.util.Optional;

/**
 * Component which validates ExtendApplication fields
 * Rules :
 *  - time of extension should be less than given in properties as maximum time,
 *  - loan must be stored in database ( must be found by id ),
 *  - loan can be extended only once
 */
@Slf4j
@Component
public class ExtendApplicationValidator extends ValidatorAdapter<ExtendApplication> {

    @Value("${app.loanMaxExtendTime}")
    private Integer loanMaxExtendTime;

    private final LoanService loanService;

    ExtendApplicationValidator(
            LoanService loanService
    ){
        this.loanService = loanService;
    }

    @Override
    public void validate(Object object, Errors errors) {
        super.validate(object, errors);
    }

    @Override
    public Class<ExtendApplication> isSupported() {
        return ExtendApplication.class;
    }

    @Override
    public void checkValid(ExtendApplication extendApplication, Errors errors) {

        log.info("Validating loan extend application ...");

        CommonValidator.validateMax(
                "loanPeriod",extendApplication.getLoanPeriod(),
                loanMaxExtendTime,
                MessagesUtils.msg("error.loanPeriod.maxExtendTimeExceeded",
                        loanMaxExtendTime),errors);

        Optional<LoanDetails> storedLoanApplication = loanService.getLoan(extendApplication.getId());

        if(storedLoanApplication.isEmpty()){
            errors.rejectValue("id", "form.id.notExist",
                    MessagesUtils.msg("error.notExist",
                    loanMaxExtendTime));
        } else {
            LoanDetails details = storedLoanApplication.get();
            if(details.getIsExtended()){
                errors.rejectValue("id", "form.id.alreadyExtended",
                        MessagesUtils.msg("error.alreadyExtended",
                        loanMaxExtendTime));
            }
        }
    }

}
