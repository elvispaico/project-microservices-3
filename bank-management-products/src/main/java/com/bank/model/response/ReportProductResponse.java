package com.bank.model.response;

import com.bank.model.entity.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportProductResponse {
    private String name;
    private String document;
    private String desTypeCustomer;
    private List<Product> products;
}
