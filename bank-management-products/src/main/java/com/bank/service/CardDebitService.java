package com.bank.service;

import com.bank.model.bean.Account;
import com.bank.model.entity.CardDebit;
import com.bank.model.request.BalanceRequest;
import com.bank.model.request.OperationRequest;
import reactor.core.publisher.Mono;

public interface CardDebitService {

    Mono<CardDebit> saveCard(CardDebit cardDebit);

    Mono<CardDebit> associatedAccount(String idCardDebit, Account account);

    Mono<CardDebit> createOperation(String idCardDebit, OperationRequest request);

    Mono<BalanceRequest> checkBalance(String idCardDebit);
}
