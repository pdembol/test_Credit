package com.pd.testCredit.baseTests;

import com.pd.testCredit.feature.loan.cotrol.LoanRepository;
import com.pd.testCredit.feature.loan.cotrol.LoanService;
import com.pd.testCredit.feature.loan.entity.LoanDetails;
import com.pd.testCredit.testObjects.LoanDetailsObjectsFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimeZone;

@RunWith(MockitoJUnitRunner.class)
public class BaseTestWithData {

    public static ZoneId LOCAL_TIME_ZONE = TimeZone.getTimeZone("Europe/Warsaw").toZoneId();

    @Spy
    public LoanRepository loanRepository;

    public LoanService loanService;

    @Before
    public void baseSetup() {

        //loading simple in memory repository with test data - extended and not extended loan details
        // to always get 1 installment paid must setup past time
        LocalDate nowDate = LocalDate.now(LOCAL_TIME_ZONE);
        LocalDate pastDate = nowDate.minusMonths(1).minusDays(5);


        LoanDetails detailsNotExtended = LoanDetailsObjectsFactory
                .getValidNotExtendedLoanApplication(12, 50000, pastDate);
        LoanDetails detailsExtended = LoanDetailsObjectsFactory
                .getValidExtendedLoanApplication(12, 50000, pastDate);

        loanRepository.insert(detailsNotExtended);
        loanRepository.insert(detailsExtended);
        loanService = new LoanService(loanRepository);
    }

}
