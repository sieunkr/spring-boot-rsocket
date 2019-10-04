package com.example.demo;

import io.rsocket.Payload;
import org.reactivestreams.Publisher;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CoffeeController {

    private final RSocketRequester requester;

    public CoffeeController(RSocketRequester requester) {
        this.requester = requester;
    }

    @GetMapping("/coffees/{name}")
    Mono<Coffee> findOneCoffeeByName(@PathVariable String name) {
        return this.requester
                .route("request-response")
                .data(new RequestCoffee(name))
                .retrieveMono(Coffee.class);
    }

    @GetMapping("/coffees")
    Flux<Coffee> findAllCoffeeList() {
        return this.requester
                .route("request-stream")
                .retrieveFlux(Coffee.class);
    }
}