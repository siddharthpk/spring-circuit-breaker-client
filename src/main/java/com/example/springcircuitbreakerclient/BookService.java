package com.example.springcircuitbreakerclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BookService {
    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);

    private final WebClient webClient;
    private final ReactiveCircuitBreaker readingListCircuitBreaker;

    public  BookService(ReactiveCircuitBreakerFactory circuitBreakerFactory){
        this.webClient = WebClient.builder().baseUrl("http://localhost:8090").build();
        this.readingListCircuitBreaker = circuitBreakerFactory.create("suggested");
    }

    public Mono<String> readingList(){
        return readingListCircuitBreaker.run(webClient.get().uri("/suggested").retrieve().bodyToMono(String.class), throwable -> {
            LOG.warn("Error: Can't complete request to Book Service", throwable);
            return Mono.just("Theory of Computation");
        });
    }
}
