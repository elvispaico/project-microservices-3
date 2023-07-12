package com.bank.model.reponse;

import com.bank.model.bean.Operation;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDebitReportResponse {
    private String idCardDebit;
    private String numCard;
    private List<Operation> operations;
}
