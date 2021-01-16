package com.pd.testCredit.feature.loan.entity;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * Entity containing fields of credit period extension application
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExtendApplication implements Serializable {
    /**
     * Id of application which is requested to extend
     */
    @NonNull
    private UUID id;
    /**
     * Period of extension
     */
    @NonNull
    private Integer loanPeriod;

}