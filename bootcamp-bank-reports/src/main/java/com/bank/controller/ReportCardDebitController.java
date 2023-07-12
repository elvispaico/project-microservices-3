package com.bank.controller;

import com.bank.model.reponse.CardDebitReportResponse;
import com.bank.service.ReportCardDebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bank/reports/carddebit")
public class ReportCardDebitController {

    private final ReportCardDebitService reportCardDebitService;

    @GetMapping("/{idCardDebit}/operations")
    public Mono<CardDebitReportResponse> generateReport( @PathVariable String idCardDebit) {
        return reportCardDebitService.generateReportOperations(idCardDebit);
    }
}
