package com.pd.testCredit.feature.loan.entity;

import lombok.*;

import javax.validation.constraints.NotNull;
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
    @NotNull
    private UUID id;
    /**
     * Period of extension
     */
    @NotNull
    private Integer loanPeriod;

}