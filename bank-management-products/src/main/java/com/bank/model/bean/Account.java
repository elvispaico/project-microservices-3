package com.bank.model.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    private String idAccount;
    private String codType;
    private String desType;
}
