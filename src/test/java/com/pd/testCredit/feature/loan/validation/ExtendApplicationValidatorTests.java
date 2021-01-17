package com.pd.testCredit.feature.loan.validation;

import com.pd.testCredit.baseTests.BaseTestWithData;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.testObjects.ExtendApplicationObjectsFactory;
import com.pd.testCredit.testObjects.UUIDSFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ExtendApplicationValidatorTests extends BaseTestWithData {

    private ExtendApplicationValidator extendApplicationValidator;

    @Before
    public void setup() {
        extendApplicationValidator = new ExtendApplicationValidator(loanService);
        ReflectionTestUtils.setField(extendApplicationValidator, "loanMaxExtendTime", 6);
    }


    @Test
    public void shouldAcceptExtendApplication() {
        //given:
        ExtendApplication extendApplication = ExtendApplicationObjectsFactory
                .getValidTestExtendApplication(4);
        Errors errors = new BeanPropertyBindingResult(extendApplication, "");

        //when:
        extendApplicationValidator.validate(extendApplication,errors);

        //then:
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shoulRejectApplicationBecauseApplicationAlreadyExtended() {
        //given:
        ExtendApplication extendApplication = ExtendApplicationObjectsFactory
                .getValidTestExtendApplication(4);
        extendApplication.setId(UUIDSFactory.uuid2);
        Errors errors = new BeanPropertyBindingResult(extendApplication, "");

        //when:
        extendApplicationValidator.validate(extendApplication,errors);

        //then:
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("id"));
    }

    @Test
    public void shouldRejectApplicationBecauseLoanToExtendNotFound() {
        //given:
        ExtendApplication extendApplication = ExtendApplicationObjectsFactory
                .getValidTestExtendApplication(4);
        extendApplication.setId(UUID.randomUUID());
        Errors errors = new BeanPropertyBindingResult(extendApplication, "");

        //when:
        extendApplicationValidator.validate(extendApplication,errors);

        //then:
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("id"));

    }


    @Test
    public void shouldRejectApplicationBecauseMaxExtendTimeIsExceeded() {
        //given:
        ExtendApplication extendApplication = ExtendApplicationObjectsFactory
                .getValidTestExtendApplication(100);
        extendApplication.setId(UUIDSFactory.uuid1);
        Errors errors = new BeanPropertyBindingResult(extendApplication, "");

        //when:
        extendApplicationValidator.validate(extendApplication,errors);

        //then:
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("loanPeriod"));

    }


}
