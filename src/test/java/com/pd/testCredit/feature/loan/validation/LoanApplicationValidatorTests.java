package com.pd.testCredit.feature.loan.validation;

import com.pd.testCredit.core.validation.CommonValidator;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.testObjects.LoanApplicationObjectsFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanApplicationValidatorTests {

    private LoanApplicationValidator loanApplicationValidator;

    @Before
    public void setup() {

        LocalDateTime datetime = LocalDateTime.of(2020, 5, 24, 3, 0);
        Instant instant = ZonedDateTime.of(datetime, ZoneId.systemDefault()).toInstant();

        // must fake clock to check time validator
        Clock fixedClock = Clock.fixed(instant, ZoneId.systemDefault());
        CommonValidator commonValidator = new CommonValidator(fixedClock);

        loanApplicationValidator = new LoanApplicationValidator(commonValidator);
        ReflectionTestUtils.setField(loanApplicationValidator, "loanAmountMax", 100000);
        ReflectionTestUtils.setField(loanApplicationValidator, "loanAmountMin", 3000);
        ReflectionTestUtils.setField(loanApplicationValidator, "loanPeriodMax", 48);
        ReflectionTestUtils.setField(loanApplicationValidator, "loanPeriodMin", 3);
    }

    @Test
    public void shouldAcceptApplication() {
        //given:
        LoanApplication application = LoanApplicationObjectsFactory.getValidLoanApplication(10,20000);
        Errors errors = new BeanPropertyBindingResult(application, "");

        //when:
        loanApplicationValidator.validate(application,errors);

        //then:
        assertFalse(errors.hasErrors());
    }


    @Test
    public void shouldRejectApplicationBecauseOfTooShortPeriod() {
        //given:
        LoanApplication application = LoanApplicationObjectsFactory.getValidLoanApplication(2,20000);
        Errors errors = new BeanPropertyBindingResult(application, "");

        //when:
        loanApplicationValidator.validate(application,errors);

        //then:
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("loanPeriod"));
    }

    @Test
    public void shouldRejectApplicationBecauseOfTooLongPeriod() {
        //given:
        LoanApplication application = LoanApplicationObjectsFactory.getValidLoanApplication(100,20000);
        Errors errors = new BeanPropertyBindingResult(application, "");

        //when:
        loanApplicationValidator.validate(application,errors);

        //then:
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("loanPeriod"));
    }

    @Test
    public void shouldRejectApplicationBecauseOfTooLowAmount() {
        //given:
        LoanApplication application = LoanApplicationObjectsFactory.getValidLoanApplication(10,100);
        Errors errors = new BeanPropertyBindingResult(application, "");

        //when:
        loanApplicationValidator.validate(application,errors);

        //then:
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("loanAmount"));
    }

    @Test
    public void shouldRejectApplicationBecauseOfTooHighAmount() {
        //given:
        LoanApplication application = LoanApplicationObjectsFactory.getValidLoanApplication(10,200000);
        Errors errors = new BeanPropertyBindingResult(application, "");

        //when:
        loanApplicationValidator.validate(application,errors);

        //then:
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("loanAmount"));
    }

    @Test
    public void shouldRejectApplicationBecauseRequestedMaxAmountTooLateAtNight() {
        //given:
        LoanApplication application = LoanApplicationObjectsFactory.getValidLoanApplication(10,100000);
        Errors errors = new BeanPropertyBindingResult(application, "");

        //when:
        loanApplicationValidator.validate(application,errors);

        //then:
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("loanAmount"));
    }



}