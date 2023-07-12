package com.bank.controller;

import com.bank.model.entity.Wallet;
import com.bank.model.request.OperationRequest;
import com.bank.model.request.WalletSaveRequest;
import com.bank.model.response.MessageResponse;
import com.bank.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bank/wallets")
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public Mono<Wallet> create(@RequestBody WalletSaveRequest request) {
        return walletService.createWallet(request);
    }

    @PutMapping("/{numCellPhone}")
    public Mono<ResponseEntity<MessageResponse>> createOperation(@PathVariable String numCellPhone,
                                                                 @RequestBody OperationRequest request) {
        return walletService.saveOperation(numCellPhone, request)
                .map(wallet -> new ResponseEntity<>(
                        new MessageResponse(HttpStatus.OK.value(), "Operation save success"),
                        HttpStatus.OK
                ));
    }
}
