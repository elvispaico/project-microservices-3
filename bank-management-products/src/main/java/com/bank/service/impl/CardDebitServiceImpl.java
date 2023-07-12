package com.bank.service.impl;

import com.bank.api.CustomerApi;
import com.bank.enums.TypeAccount;
import com.bank.exception.AttributeException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.model.bean.Account;
import com.bank.model.entity.CardDebit;
import com.bank.model.entity.Product;
import com.bank.model.request.BalanceRequest;
import com.bank.model.request.OperationRequest;
import com.bank.repository.CardDebitRepository;
import com.bank.repository.ProductRepository;
import com.bank.service.CardDebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CardDebitServiceImpl implements CardDebitService {

    private final CardDebitRepository cardDebitRepository;
    private final ProductRepository productRepository;
    private final CustomerApi customerApi;

    @Override
    public Mono<CardDebit> saveCard(CardDebit cardDebit) {
        return customerApi.findCustomerById(cardDebit.getIdCustomer())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer not found")))
                .flatMap(customerResponse -> cardDebitRepository.save(cardDebit));
    }


    @Override
    public Mono<CardDebit> associatedAccount(String idCardDebit, Account account) {
        return cardDebitRepository.findById(idCardDebit)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Cards debit not found")))
                .flatMap(cardDebit -> {
                    if (cardDebit.getAccounts() == null) {
                        cardDebit.setAccounts(List.of(account));
                        return cardDebitRepository.save(cardDebit);
                    } else {
                        var existAccount = cardDebit.getAccounts().stream()
                                .anyMatch(itemAccount -> itemAccount.getIdAccount().equals(account.getIdAccount()) ||
                                        account.getCodType().equals(TypeAccount.PRINCIPAL.getValue()));
                        if (!existAccount) {
                            cardDebit.getAccounts().add(account);
                            return cardDebitRepository.save(cardDebit);
                        } else {
                            return Mono.error(new AttributeException("the account is already associated o exists account primary"));
                        }
                    }
                });
    }

    @Override
    public Mono<CardDebit> createOperation(String idCardDebit, OperationRequest request) {

        return cardDebitRepository.findById(idCardDebit)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Cards debit not found")))
                .flatMap(cardDebit -> {
                    if (Objects.isNull(cardDebit.getAccounts())) {
                        return Mono.error(new AttributeException("not found account associated"));
                    } else {
                        return getAccountWithAmount(Mono.just(cardDebit.getAccounts()), request.getAmount())
                                .switchIfEmpty(Mono.error(new AttributeException("Insufficient funds")))
                                .flatMap(product -> {
                                    product.setBalance(product.getBalance() - request.getAmount());
                                    return productRepository.save(product)
                                            .flatMap(productSave -> {
                                                var listOperations = Objects.isNull(cardDebit.getOperations()) ? new ArrayList<OperationRequest>()
                                                        : cardDebit.getOperations();
                                                listOperations.add(request);
                                                cardDebit.setOperations(listOperations);
                                                return cardDebitRepository.save(cardDebit);
                                            });
                                });
                    }
                });
    }

    @Override
    public Mono<BalanceRequest> checkBalance(String idCardDebit) {
        return cardDebitRepository.findById(idCardDebit)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Cards debit not found")))
                .flatMap(cardDebit -> getAccountPrincipal(Mono.just(cardDebit.getAccounts()))
                        .flatMap(account -> productRepository.findById(account.getIdAccount())
                                .map(product -> BalanceRequest.builder()
                                        .idAccount(product.getId())
                                        .amount(product.getBalance())
                                        .build()))
                );
    }

    private Mono<Product> getAccountWithAmount(Mono<List<Account>> monoAccounts, double amount) {

        return monoAccounts
                .flatMapMany(Flux::fromIterable)
                .filter(account -> account.getCodType().equals(TypeAccount.PRINCIPAL.getValue()))
                .flatMap(account -> productRepository.findById(account.getIdAccount()))
                .filter(product -> product.getBalance() >= amount)
                .switchIfEmpty(monoAccounts
                        .flatMapMany(Flux::fromIterable)
                        .filter(account -> account.getCodType().equals(TypeAccount.SECUNDARIO.getValue()))
                        .flatMap(account -> productRepository.findById(account.getIdAccount())
                                .filter(product -> product.getBalance() >= amount))
                        .next()
                )
                .next();
    }

    private Mono<Account> getAccountPrincipal(Mono<List<Account>> monoAcounts) {
        return monoAcounts
                .flatMapMany(Flux::fromIterable)
                .filter(account -> account.getCodType().equals(TypeAccount.PRINCIPAL.getValue()))
                .switchIfEmpty(Mono.empty())
                .next();
    }
}
