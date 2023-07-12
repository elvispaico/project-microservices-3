package com.bank.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationRequest {
    private String codTypeOperation;
    private String numOperation;
    private double amount;
}
