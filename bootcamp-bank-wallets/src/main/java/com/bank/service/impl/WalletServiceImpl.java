package com.bank.service.impl;

import com.bank.enums.TypeOperation;
import com.bank.exception.AttributeException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.kafka.KafkaProducer;
import com.bank.model.bean.Operation;
import com.bank.model.entity.Wallet;
import com.bank.model.request.OperationRequest;
import com.bank.model.request.WalletSaveRequest;
import com.bank.repository.WalletRepository;
import com.bank.service.WalletService;
import com.bank.service.mapper.WalletMapper;
import com.bank.util.LogicUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public Mono<Wallet> createWallet(WalletSaveRequest request) {
        return walletRepository.findByNumDocumentOrNumCellphone(request.getNumDocument(), request.getNumCellphone())
                .hasElement()
                .flatMap(exitsWallet -> exitsWallet ? Mono.error(new AttributeException("Wallet exists con number document or number phone"))
                        : walletRepository.save(WalletMapper.mapWalletSaveRequestToWallet(request))
                );
    }

    @Override
    public Mono<Wallet> saveOperation(String numCellphone, OperationRequest request) {
        return walletRepository.findByNumCellphone(numCellphone)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Wallet not found")))
                .flatMap(wallet -> {
                    var lisOperations = Objects.isNull(wallet.getOperations()) ? new ArrayList<Operation>()
                            : wallet.getOperations();
                    var numOperation = LogicUtil.generateNumOperation(Optional.ofNullable(wallet.getOperations()));
                    lisOperations.add(Operation.builder()
                            .numOperation(numOperation)
                            .codTypeOperation(request.getCodTypeOperation())
                            .amount(request.getAmount())
                            .build());
                    wallet.setOperations(lisOperations);
                    double balance = request.getCodTypeOperation().equals(TypeOperation.PAY.getValue())
                            ? wallet.getBalance() - request.getAmount()
                            : wallet.getBalance() + request.getAmount();
                    wallet.setBalance(balance);

                    if (wallet.getBalance() >= 0.0) {
                        kafkaProducer.send(wallet, numOperation);
                        return walletRepository.save(wallet);
                    } else {
                        return Mono.error(new AttributeException("insufficient balance"));
                    }
                });
    }

}
