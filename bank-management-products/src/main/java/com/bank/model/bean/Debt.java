package com.bank.model.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Debt {
    private int numDebt;
    private boolean expired;
}
