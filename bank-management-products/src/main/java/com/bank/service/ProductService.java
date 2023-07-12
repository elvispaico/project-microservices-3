package com.bank.service;

import com.bank.model.entity.Product;
import com.bank.model.response.ReportTransactionsResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    /**
     * Metodo que guarda un producto en la base de datos
     *
     * @param request
     * @return
     */
    Mono<Product> save(Product request);

    /**
     * Metodo para buscar un producto por ID, que representa
     * la clave primaria
     *
     * @param id clave primaria
     * @return Objeto Single
     */
    Mono<Product> findById(String id);

    Flux<Product> findAllProductByIdCustomer(String idCustomer);

    Mono<ReportTransactionsResponse> findProductWhitTransactionCommission(String idProduct);

    Mono<Product> saveAccountPersonalVip(Product request);
}
