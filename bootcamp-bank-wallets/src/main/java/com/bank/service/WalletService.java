package com.bank.service;

import com.bank.model.entity.Wallet;
import com.bank.model.request.WalletSaveRequest;
import reactor.core.publisher.Mono;

public interface WalletService {

    Mono<Wallet> create(WalletSaveRequest request);
}
