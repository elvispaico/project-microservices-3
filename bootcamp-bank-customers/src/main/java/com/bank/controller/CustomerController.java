package com.bank.controller;


import com.bank.openapi.api.CustomersApi;
import com.bank.openapi.model.dto.CustomerResponse;
import com.bank.openapi.model.dto.CustomerSaveRequest;
import com.bank.openapi.model.dto.CustomerUpdateRequest;
import com.bank.openapi.model.dto.MessageResponse;
import com.bank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bank/v1")
public class CustomerController implements CustomersApi {

    private final CustomerService customerService;

    @Override
    public Mono<ResponseEntity<MessageResponse>> createCustomer(Mono<CustomerSaveRequest> customerSaveRequest, ServerWebExchange exchange) {
        return customerService.save(customerSaveRequest)
                .map(customer -> {
                    MessageResponse response = new MessageResponse();
                    response.setCode(HttpStatus.CREATED.value());
                    response.setMessage("Custom save success");
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                });
    }

    @Override
    public Mono<ResponseEntity<MessageResponse>> editCustomer(String id, Mono<CustomerUpdateRequest> customerUpdateRequest, ServerWebExchange exchange) {
        return customerService.update(id,customerUpdateRequest)
                .map(customer -> {
                    MessageResponse response = new MessageResponse();
                    response.setCode(HttpStatus.CREATED.value());
                    response.setMessage("Custom save success");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                });
    }

    @Override
    public Mono<ResponseEntity<Flux<CustomerResponse>>> findAllCustomer(ServerWebExchange exchange) {
        return Mono.just(new ResponseEntity<>(customerService.findAllCustomers(), HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> findCustomerById(String id, ServerWebExchange exchange) {
        return customerService.findById(id)
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK));
    }
}
