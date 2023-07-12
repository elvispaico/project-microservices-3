package com.bank.repository;

import com.bank.model.entity.CardDebit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CardDebitRepository extends ReactiveMongoRepository<CardDebit,String> {
}
