package com.bank.controlller;

import com.bank.model.bean.Account;
import com.bank.model.entity.CardDebit;
import com.bank.model.request.BalanceRequest;
import com.bank.model.request.OperationRequest;
import com.bank.model.response.MessageResponse;
import com.bank.service.CardDebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bank/carddebits")
public class CardDebitController {

    private final CardDebitService cardDebitService;

    @PostMapping
    public Mono<CardDebit> createCardDebit(@RequestBody CardDebit request) {
        return cardDebitService.saveCard(request);
    }

    @PutMapping("/{idCardDebit}/associated")
    public Mono<ResponseEntity<MessageResponse>> associatedAccounts(@PathVariable String idCardDebit,
                                                                    @RequestBody Account account) {
        return cardDebitService.associatedAccount(idCardDebit, account)
                .map(cardDebit -> new ResponseEntity<>(new MessageResponse(HttpStatus.OK.value(), "Associated success")
                        , HttpStatus.OK));
    }

    @PutMapping("/{idCardDebit}/operations")
    public Mono<ResponseEntity<MessageResponse>> createOperations(@PathVariable String idCardDebit,
                                                                  @RequestBody OperationRequest request) {
        return cardDebitService.createOperation(idCardDebit, request)
                .map(cardDebit -> new ResponseEntity<>(new MessageResponse(HttpStatus.OK.value(), "Operation save success")
                        , HttpStatus.OK));
    }

    @GetMapping("/{idCardDebit}/checkBalance")
    public Mono<BalanceRequest> checkBalance(@PathVariable String idCardDebit) {
        return cardDebitService.checkBalance(idCardDebit);
    }
}
