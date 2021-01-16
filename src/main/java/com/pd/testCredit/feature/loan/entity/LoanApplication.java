package com.pd.testCredit.feature.loan.entity;

import lombok.*;

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
    @NonNull
    private String    name;
    /**
     * The applicants surname
     */
    @NonNull
    private String    surname;
    /**
     * The applicants id number
     */
    @NonNull
    private String    idNumber;
    /**
     * Requested loan amount
     */
    @NonNull
    private Integer   loanAmount;
    /**
     * Requested loan period
     */
    @NonNull
    private Integer   loanPeriod;

}