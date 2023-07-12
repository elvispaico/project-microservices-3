package com.bank.service;

import com.bank.openapi.model.dto.ReportProductResponse;
import reactor.core.publisher.Mono;

public interface ReportProductService {

    Mono<ReportProductResponse> generateReportProductByCustomer(String idCustomer);
}
