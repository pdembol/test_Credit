package com.pd.testCredit.feature.loan.entity;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Entity containing fields of loan application
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    /**
     * The applicants name
     */
    @NotNull
    private String    name;
    /**
     * The applicants surname
     */
    @NotNull
    private String    surname;
    /**
     * The applicants id number
     */
    @NotNull
    private String    idNumber;
    /**
     * Requested loan amount
     */
    @NotNull
    private Integer   loanAmount;
    /**
     * Requested loan period
     */
    @NotNull
    private Integer   loanPeriod;

}