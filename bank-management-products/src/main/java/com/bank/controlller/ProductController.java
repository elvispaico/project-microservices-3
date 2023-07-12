package com.bank.controlller;

import com.bank.model.entity.Product;
import com.bank.model.response.ReportTransactionsResponse;
import com.bank.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bank/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Mono<Product> save(@Validated @RequestBody Product product) {
        return productService.save(product);

    }

    @PostMapping("/vip")
    public Mono<Product> saveAccountPersonalVip(@RequestBody Product request) {
        return productService.saveAccountPersonalVip(request);
    }

    @GetMapping("/{id}")
    public Mono<Product> fingProductById(@PathVariable String id) {
        return productService.findById(id);
    }

    @GetMapping("/customer/{idCustomer}")
    public Flux<Product> findAllProductsByCustomer(@PathVariable String idCustomer) {
        return productService.findAllProductByIdCustomer(idCustomer);
    }

    @GetMapping("/{idProduct}/commissions")
    public Mono<ReportTransactionsResponse> findProductWhitCommissions(@PathVariable String idProduct) {
        return productService.findProductWhitTransactionCommission(idProduct);
    }
}
