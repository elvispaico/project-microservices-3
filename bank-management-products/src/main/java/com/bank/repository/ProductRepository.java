package com.bank.repository;

import com.bank.model.entity.Product;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

    /**
     * Metodo que busca un producto por ID,
     * usando query basado en mongodb
     *
     * @param idCustomer
     * @return
     */
    @Query("{'idCustomer' :  ?0 }")
    Flux<Product> findByCustomer(String idCustomer);

    Flux<Product> findAllByIdCustomer(String idCustomer);

    @ExistsQuery("{ 'idCustomer': ?0  ,'codTypeService': '06' }")
    Mono<Boolean> existsProductCard(String idCustomer);

    @ExistsQuery("{ 'idCustomer': ?0  ,'codTypeService': '02' }")
    Mono<Boolean> existsProductAccountCurrent(String idCustomer);
}
