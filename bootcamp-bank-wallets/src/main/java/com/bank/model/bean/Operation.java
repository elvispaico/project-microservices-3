package com.bank.model.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operation {
    private String numOperation;
    private String codTypeOperation;
    private double amount;
}
