package com.bank.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletSaveRequest {
    private String numDocument;
    private String numCellphone;
    private String imei;
    private String email;
    private double balance;
}
