package com.pd.testCredit.testObjects;

import com.pd.testCredit.feature.loan.entity.LoanApplication;

public class LoanApplicationObjectsFactory {

    public static LoanApplication getValidLoanApplication(Integer loanPeriod, Integer loanAmount) {

        return new LoanApplication("John","Doe","cdp123123123",loanAmount,loanPeriod);

    }

}
