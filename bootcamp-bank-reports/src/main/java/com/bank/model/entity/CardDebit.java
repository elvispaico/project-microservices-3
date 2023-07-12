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
@Document(value = "carddebits")
public class CardDebit {
    @Id
    private String id;
    private String idCustomer;
    private String numCard;
    private List<Operation> operations;
}
