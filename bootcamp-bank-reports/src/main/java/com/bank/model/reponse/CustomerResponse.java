package com.bank.model.reponse;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private String name;
    private String numDocument;
    private String desTypeCustomer;
}
