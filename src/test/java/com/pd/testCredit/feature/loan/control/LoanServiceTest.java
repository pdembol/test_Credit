package com.pd.testCredit.feature.loan.control;

import com.pd.testCredit.baseTests.BaseTestWithData;
import com.pd.testCredit.core.utils.MathUtils;
import com.pd.testCredit.feature.loan.entity.ExtendApplication;
import com.pd.testCredit.feature.loan.entity.LoanApplication;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import com.pd.testCredit.testObjects.ExtendApplicationObjectsFactory;
import com.pd.testCredit.testObjects.LoanApplicationObjectsFactory;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest extends BaseTestWithData {

    @Test
    public void shouldAcceptLoanAndReturnCorrectlyCalculatedDetails() {
        //given:
        LoanApplication application = LoanApplicationObjectsFactory
                .getValidLoanApplication(10, 20000);
        LocalDate nowDate = LocalDate.now(LOCAL_TIME_ZONE);

        //when:
        LoanDetails details = loanService.submitApplication(application);

        //then:
        assertEquals(2140, (float) details.getAmountOfInstallment());
        assertEquals(21400, (float) details.getTotalAmount());
        assertEquals(20000, (float) details.getLoanAmount());
        assertEquals(nowDate.plusMonths(10).toString(), details.getLastInstallmentDate().toString());

    }

    @Test
    public void shouldAcceptLoanExtensionAndReturnCorrectlyCalculatedDetails() {
        //given:
        int newLoanPeriod = 5;
        ExtendApplication extendApplication = ExtendApplicationObjectsFactory
                .getValidTestExtendApplication(newLoanPeriod);
        LocalDate nowDate = LocalDate.now(LOCAL_TIME_ZONE);
        //mocked in db applicationDate
        LocalDate pastDate = nowDate.minusMonths(1).minusDays(5);

        //when:
        LoanDetails details = loanService.submitExtension(extendApplication);

        //then:
        assertEquals(MathUtils.roundToTwoDecimals((details.getTotalAmount() - details.getPaidAmount())
                / details.getRemainingInstallments()), details.getAmountOfInstallment());
        assertEquals(53500, details.getTotalAmount());
        assertEquals(50000, details.getLoanAmount());
        assertEquals(12 + newLoanPeriod, details.getLoanPeriod());
        assertEquals(11 + newLoanPeriod, details.getRemainingInstallments());
        assertTrue(details.getIsExtended());
        assertEquals(pastDate.plusMonths(12 + newLoanPeriod).toString(),
                details.getLastInstallmentDate().toString());
    }


}

