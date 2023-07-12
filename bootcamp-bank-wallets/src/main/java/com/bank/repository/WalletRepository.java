package com.bank.repository;

import com.bank.model.entity.Wallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface WalletRepository extends ReactiveMongoRepository<Wallet, String> {

    Mono<Wallet> findByNumDocumentOrNumCellphone(String numDocument,String numCellPhone);

    Mono<Wallet> findByNumCellphone(String nuCellPhone);

}
