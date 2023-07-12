package com.bank.service;

import com.bank.model.entity.Customer;


import com.bank.openapi.model.dto.CustomerResponse;
import com.bank.openapi.model.dto.CustomerSaveRequest;
import com.bank.openapi.model.dto.CustomerUpdateRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    /**
     * Metodo para guardar un cliente
     *
     * @param monoRequest Objeto para guardar solo los campos necesarios
     * @return
     */
    Mono<Customer> save( Mono<CustomerSaveRequest> monoRequest);

    /**
     * Metodo para actualizar datos del cliente, solo nombre
     *
     * @param request
     * @return
     */
    Mono<Customer> update( String idCustomer,Mono<CustomerUpdateRequest> request);

    /**
     * Metodo que busca un cliente por el ID, que representa la
     * clave primaria
     *
     * @param id
     * @return
     */
    Mono<CustomerResponse> findById(String id);


    /**
     * Metodo que retorna todos los clientes registrado
     *
     * @return
     */
    Flux<CustomerResponse> findAllCustomers();

}
