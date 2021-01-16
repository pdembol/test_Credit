package com.pd.testCredit.testObjects;

import com.pd.testCredit.feature.loan.entity.ExtendApplication;

public class ExtendApplicationObjectsFactory {

    public static ExtendApplication getValidTestExtendApplication(Integer loanPeriod) {
        return ExtendApplication.builder()
                .id(UUIDSFactory.uuid1)
                .loanPeriod(loanPeriod)
                .build();

    }

}
