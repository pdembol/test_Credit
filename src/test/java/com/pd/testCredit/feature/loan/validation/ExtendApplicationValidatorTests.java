package com.pd.testCredit.feature.loan.validation;

import com.pd.testCredit.core.validation.CommonValidator;
import com.pd.testCredit.feature.loan.cotrol.LoanRepository;
import com.pd.testCredit.feature.loan.cotrol.LoanService;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import com.pd.testCredit.testObjects.ExtendApplicationObjectsFactory;
import com.pd.testCredit.testObjects.LoanDetailsObjectsFactory;
import com.pd.testCredit.testObjects.UUIDSFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ExtendApplicationValidatorTests {

    @Mock
    LoanRepository loanRepository;

    @Mock
    CommonValidator commonValidator;

    @Mock
    LoanService loanService;

    @InjectMocks
    ExtendApplicationValidator extendApplicationValidator;


    @BeforeEach
    void setup() {
        LocalDate date = LocalDate.of(2021, Month.JANUARY, 8);
        LoanDetails details = LoanDetailsObjectsFactory.getValidLoanApplication(45,50000,date);
        loanRepository.insert(details);
    }

    @AfterEach
    void cleanUp() {
        loanRepository.clear();
    }

    @Test
    public void shouldSuccessfullyExtendLoan() {
        //given:
        ExtendApplication extendApplication = ExtendApplicationObjectsFactory.getValidTestExtendApplication(4);
        Errors errors = new BeanPropertyBindingResult(extendApplication, "");

        //when:
        extendApplicationValidator.validate(extendApplication,errors);

        //then:
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotExtendBecauseAlreadyExtended() {
        //given:
        LocalDate date = LocalDate.of(2021, Month.JANUARY, 8);
        LoanDetails details = LoanDetailsObjectsFactory.getValidLoanApplication(45,50000,date);
        details.setId(UUIDSFactory.uuid2);
        details.setIsExtended(true);
        loanRepository.insert(details);

        ExtendApplication extendApplication = ExtendApplicationObjectsFactory.getValidTestExtendApplication(4);
        extendApplication.setId(UUIDSFactory.uuid2);
        Errors errors = new BeanPropertyBindingResult(extendApplication, "");

        //when:
        extendApplicationValidator.validate(extendApplication,errors);

        //then:
        assertTrue(errors.hasErrors());
    }

    @Test
    public void shouldThrowErrorBecauseLoanToExtendNotFound() {
        //given too short name:
        ExtendApplication extendApplication = ExtendApplicationObjectsFactory.getValidTestExtendApplication(4);
        extendApplication.setId(UUIDSFactory.uuid2);
        Errors errors = new BeanPropertyBindingResult(extendApplication, "");

        //when:
        extendApplicationValidator.validate(extendApplication,errors);

        //then:
        assertTrue(errors.hasErrors());
    }


    @Test
    public void shouldThrowErrorBecauseMaxExtendTimeIsExceeded() {
        //given too short name:
        ExtendApplication extendApplication = ExtendApplicationObjectsFactory.getValidTestExtendApplication(100);
        extendApplication.setId(UUIDSFactory.uuid1);
        Errors errors = new BeanPropertyBindingResult(extendApplication, "");

        //when:
        extendApplicationValidator.validate(extendApplication,errors);

        //then:
        assertTrue(errors.hasErrors());
    }


}
