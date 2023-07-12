package com.bank.service;

import com.bank.model.reponse.CardDebitReportResponse;
import reactor.core.publisher.Mono;

public interface ReportCardDebitService {

    Mono<CardDebitReportResponse> generateReportOperations(String idCarddebit);
}
