package com.bank.service.impl;

import com.bank.exception.AttributeException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.mapper.CustomerMapper;
import com.bank.model.entity.Customer;
import com.bank.openapi.model.dto.CustomerResponse;
import com.bank.openapi.model.dto.CustomerSaveRequest;
import com.bank.openapi.model.dto.CustomerUpdateRequest;
import com.bank.repository.CustomerRepository;
import com.bank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<Customer> save(Mono<CustomerSaveRequest> monoRequest) {

        return monoRequest
                .flatMap(customerSaveRequest -> customerRepository.findByNumDocument(customerSaveRequest.getNumDocument())
                        .hasElement()
                        .flatMap(existsCustomer -> existsCustomer ? Mono.error(new AttributeException("Customer exists"))
                                : customerRepository.save(CustomerMapper.mapRequestToEntity(customerSaveRequest))));
    }

    @Override
    public Mono<Customer> update(String idCustomer, Mono<CustomerUpdateRequest> monoRequest) {
        return monoRequest.flatMap(request -> customerRepository.findById(idCustomer)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer not found")))
                .flatMap(customer -> {
                    customer.setName(request.getName());
                    customer.setNumDocument(request.getNumDocument());
                    return customerRepository.save(customer);
                }));

    }

    @Override
    public Mono<CustomerResponse> findById(String id) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer not found")))
                .map(CustomerMapper::mapCustomerToCustomerResponse);
    }

    @Override
    public Flux<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .map(CustomerMapper::mapCustomerToCustomerResponse);
    }


}
