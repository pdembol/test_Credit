package com.pd.testCredit.feature.loan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.UUID;

/**
 * Entity containing fields of credit period extension application
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtendApplication implements Serializable {
    /**
     * Id of application which is requested to extend
     */
    private UUID id;
    /**
     * Period of extension
     */
    private Integer loanPeriod;

}