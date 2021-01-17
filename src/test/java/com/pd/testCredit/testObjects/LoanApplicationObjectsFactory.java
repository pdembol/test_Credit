package com.pd.testCredit.testObjects;

import com.pd.testCredit.feature.loan.entity.LoanApplication;

public class LoanApplicationObjectsFactory {

    public static LoanApplication getValidLoanApplication(Integer loanPeriod, Integer loanAmount) {
        return new LoanApplication("Jimmy","Tudesky","cdp321321321",loanAmount,loanPeriod);
    }

}
