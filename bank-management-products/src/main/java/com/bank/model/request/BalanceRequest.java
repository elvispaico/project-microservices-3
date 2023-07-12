package com.bank.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceRequest {
    private String idAccount;
    private double amount;
}
