package com.bank.repository;

import com.bank.model.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

    Flux<Product> findAllByIdCustomer(String idCustomer);


}
