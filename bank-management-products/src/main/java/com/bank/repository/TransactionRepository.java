package com.bank.repository;

import com.bank.model.entity.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction,String> {

    Flux<Transaction> findByIdProduct(String idProduct);
}
