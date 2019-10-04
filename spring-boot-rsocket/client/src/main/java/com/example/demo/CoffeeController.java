package com.example.demo;

import io.rsocket.Payload;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CoffeeController {

    private final RSocketRequester requester;

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

    @PostMapping("/coffees/{name}")
    Mono<Void> addCoffee(@PathVariable String name) {
        return this.requester
                .route("fire-forget")
                .data(new RequestCoffee(name))
                .send();
    }
}