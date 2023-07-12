package com.bank.model.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operation {
    private String codTypeOperation;
    private String numOperation;
    private double amount;
}
