package com.bank.service;

import com.bank.model.entity.Wallet;
import com.bank.model.request.OperationRequest;
import com.bank.model.request.WalletSaveRequest;
import reactor.core.publisher.Mono;

public interface WalletService {

    Mono<Wallet> createWallet(WalletSaveRequest request);

    Mono<Wallet> saveOperation(String numCellphone,OperationRequest request);
}
