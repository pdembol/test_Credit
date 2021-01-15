package com.pd.testCredit.feature.loan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication implements Serializable {
    private String    name;
    private String    surname;
    private String    idNumber;
    private Integer   loanAmount;
    private Integer   loanPeriod;

}