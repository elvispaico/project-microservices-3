package com.bank.controller;

import com.bank.openapi.api.ProductsApi;
import com.bank.openapi.model.dto.ReportProductResponse;
import com.bank.service.ReportProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bank/reports")
public class ReportProductController implements ProductsApi {

    private final ReportProductService reportProductService;

    @Override
    public Mono<ResponseEntity<ReportProductResponse>> generateReport(String idCustomer, ServerWebExchange exchange) {
        return reportProductService.generateReportProductByCustomer(idCustomer)
                .map(reportProductResponse -> new ResponseEntity<>(reportProductResponse, HttpStatus.OK));
    }
}
