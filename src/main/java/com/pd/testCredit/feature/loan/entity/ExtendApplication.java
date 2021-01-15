package com.pd.testCredit.feature.loan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtendApplication implements Serializable {

    private UUID id;
    private Integer loanPeriod;

}