package com.bank.api;


import com.bank.model.response.CustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class CustomerApi {

    private final WebClient webClient;


    public CustomerApi(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081")
                .build();
    }

    public Mono<CustomerResponse> findCustomerById(String idCustomer) {
        return webClient.get()
                .uri("/api/bank/v1/customers/" + idCustomer)
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.empty();
                    } else {
                        return Mono.error(ex);
                    }
                });
    }
}
