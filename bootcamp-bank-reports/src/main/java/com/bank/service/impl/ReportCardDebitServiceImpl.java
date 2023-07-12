package com.bank.service.impl;

import com.bank.exception.ResourceNotFoundException;
import com.bank.model.reponse.CardDebitReportResponse;
import com.bank.repository.CardDebitRepository;
import com.bank.service.ReportCardDebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ReportCardDebitServiceImpl implements ReportCardDebitService {

    private final CardDebitRepository cardDebitRepository;

    @Override
    public Mono<CardDebitReportResponse> generateReportOperations(String idCardDebit) {
        return cardDebitRepository.findById(idCardDebit)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("card debit not found")))
                .map(cardDebit -> CardDebitReportResponse.builder()
                        .idCardDebit(cardDebit.getId())
                        .numCard(cardDebit.getNumCard())
                        .operations(cardDebit.getOperations().stream()
                                .limit(10)
                                .collect(Collectors.toList()))
                        .build());
    }
}
