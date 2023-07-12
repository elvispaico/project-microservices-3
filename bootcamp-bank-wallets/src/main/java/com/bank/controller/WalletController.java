package com.bank.controller;

import com.bank.model.entity.Wallet;
import com.bank.model.request.WalletSaveRequest;
import com.bank.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bank/wallets")
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public Mono<Wallet> create(@RequestBody WalletSaveRequest request) {
        return walletService.create(request);
    }
}
