package com.bank.service.impl;

import com.bank.api.CustomerApi;
import com.bank.exception.ResourceNotFoundException;
import com.bank.model.entity.Product;
import com.bank.openapi.model.dto.ProductResponse;
import com.bank.openapi.model.dto.ReportProductResponse;
import com.bank.repository.ProductRepository;
import com.bank.service.ReportProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ReportProductServiceImpl implements ReportProductService {

    private final ProductRepository productRepository;
    private final CustomerApi customerApi;

    @Override
    public Mono<ReportProductResponse> generateReportProductByCustomer(String idCustomer) {

        return customerApi.findCustomerById(idCustomer)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer not found")))
                .flatMap(customerResponse -> productRepository.findAllByIdCustomer(idCustomer)
                        .map(this::mapProductToProductResponse)
                        .collectList()
                        .map(productResponses -> {
                            ReportProductResponse res = new ReportProductResponse();
                            res.setName(customerResponse.getName());
                            res.setDocument(customerResponse.getNumDocument());
                            res.setDesTypeCustomer(customerResponse.getDesTypeCustomer());
                            res.setProducts(productResponses);
                            return res;
                        }));
    }

    private ProductResponse mapProductToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setCodTypeProduct(product.getCodTypeProduct());
        productResponse.setDesTypeProduct(product.getDesTypeProduct());
        productResponse.setCodTypeService(product.getCodTypeService());
        productResponse.setDesTypeService(product.getDesTypeService());
        productResponse.setBalance(product.getBalance());
        productResponse.setCommission(product.getCommission());
        return productResponse;
    }
}
