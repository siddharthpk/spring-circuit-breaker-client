package com.example.springcircuitbreakerclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RestController
@SpringBootApplication
public class  SpringCircuitBreakerClientApplication {

    @RequestMapping("/read")
    public Mono<String> Read(){
        return WebClient.builder().build()
                .get().uri("https://localhost:8090/suggested")
                .retrieve().bodyToMono(String.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCircuitBreakerClientApplication.class, args);
    }

}
