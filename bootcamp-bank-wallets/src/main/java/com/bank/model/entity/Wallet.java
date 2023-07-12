package com.bank.model.entity;

import com.bank.model.bean.Operation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(value = "wallets")
public class Wallet {
    @Id
    private String id;
    private String numDocument;
    private String numCellphone;
    private String imei;
    private String email;
    private double balance;
    private List<Operation> operations;
}
