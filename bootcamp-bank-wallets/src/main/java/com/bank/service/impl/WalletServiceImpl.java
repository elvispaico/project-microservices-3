package com.bank.service.impl;

import com.bank.exception.AttributeException;
import com.bank.model.entity.Wallet;
import com.bank.model.request.WalletSaveRequest;
import com.bank.repository.WalletRepository;
import com.bank.service.WalletService;
import com.bank.service.mapper.WalletMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Mono<Wallet> create(WalletSaveRequest request) {
       return walletRepository.findByNumDocumentOrNumCellphone(request.getNumDocument(), request.getNumCellphone())
                .hasElement()
                .flatMap(exitsWallet -> exitsWallet ? Mono.error(new AttributeException("Wallet exists con number document or number phone"))
                        : walletRepository.save(WalletMapper.mapWalletSaveRequestToWallet(request))
                );
    }
}
