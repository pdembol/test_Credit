package com.pd.testCredit.feature.loan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entity containing fields of loan application
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication implements Serializable {
    /**
     * The applicants name
     */
    private String    name;
    /**
     * The applicants surname
     */
    private String    surname;
    /**
     * The applicants id number
     */
    private String    idNumber;
    /**
     * Requested loan amount
     */
    private Integer   loanAmount;
    /**
     * Requested loan period
     */
    private Integer   loanPeriod;

}