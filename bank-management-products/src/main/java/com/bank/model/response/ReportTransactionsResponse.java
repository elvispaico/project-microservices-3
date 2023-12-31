package com.bank.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportTransactionsResponse {
    private String idProduct;
    private double balance;
    private double totalCommission;
    private List<TransactionResponse> listTransactions;
}
