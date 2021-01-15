package com.pd.testCredit.feature.loan.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
public class LoanDetails extends LoanApplication implements Serializable {

    private UUID      id;
    private Integer   interest;
    private Boolean   isExtended;
    private Integer   amountOfInstallment;
    private Integer   totalAmount;
    private Timestamp dateOfApplication;
    private Timestamp firstInstallmentDate;
    private Timestamp lastInstallmentDate;

    @Builder
    public LoanDetails(
            String    name,
            String    surname,
            String    idNumber,
            Integer   loanAmount,
            Integer   loanPeriod,
            UUID      id,
            Integer   interest,
            Boolean   isExtended,
            Integer   amountOfInstallment,
            Integer   totalAmount,
            Timestamp dateOfApplication,
            Timestamp firstInstallmentDate,
            Timestamp lastInstallmentDate
    ){
        super(name,surname,idNumber,loanAmount,loanPeriod);
        this.id = id;
        this.interest = interest;
        this.isExtended = isExtended;
        this.amountOfInstallment = amountOfInstallment;
        this.totalAmount = totalAmount;
        this.dateOfApplication = dateOfApplication;
        this.firstInstallmentDate = firstInstallmentDate;
        this.lastInstallmentDate = lastInstallmentDate;
    }
    
}