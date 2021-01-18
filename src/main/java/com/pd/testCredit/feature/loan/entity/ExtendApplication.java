package com.pd.testCredit.feature.loan.entity;

import lombok.*;
import java.util.UUID;

/**
 * Entity containing fields of credit period extension application
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExtendApplication {
    /**
     * Id of application which is requested to extend
     */
    private UUID id;
    /**
     * Period of extension
     */
    private Integer loanPeriod;

}