package com.pd.testCredit.feature.loan.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.UUID;

/**
 * Entity containing fields of loan details , which is returned when the request has been accepted
 */

@Data
@NoArgsConstructor
public class LoanDetails extends LoanApplication {

    /**
     * The loan id which is assigned when request is accepted
     */
    private UUID id;
    /**
     * The loan interest which is const and = 7%
     */
    private Integer interest;
    /**
     * The flag which informs whether the loan period has been extended
     */
    private Boolean isExtended;
    /**
     * Amount of one installment
     */
    private Float amountOfInstallment;
    /**
     * Total amount to pay
     */
    private Float totalAmount;
    /**
     * Amount already paid amount
     */
    private Float paidAmount;
    /**
     * Number of remaining installments
     */
    private Integer remainingInstallments;
    /**
     * Date when application came
     */
    private Date dateOfApplication;
    /**
     * Date of first installment ( it was assumed that = 30 days after application was accepted )
     */
    private Date firstInstallmentDate;
    /**
     * Date of last installment ( assumed that x months after first installment , where x is requested loanPeriod )
     */
    private Date lastInstallmentDate;

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
            Float   amountOfInstallment,
            Float   totalAmount,
            Float   paidAmount,
            Integer   remainingInstallments,
            Date dateOfApplication,
            Date firstInstallmentDate,
            Date lastInstallmentDate
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
        this.paidAmount = paidAmount;
        this.remainingInstallments = remainingInstallments;
    }
    
}