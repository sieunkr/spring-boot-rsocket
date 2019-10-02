package com.example.demo;

import org.reactivestreams.Publisher;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeController {

    private final RSocketRequester requester;

    public CoffeeController(RSocketRequester requester) {
        this.requester = requester;
    }

    @GetMapping("/coffee/{name}")
    Publisher<Coffee> coffee(@PathVariable String name) {
        return this.requester
                .route("coffee")
                .data(new RequestCoffee(name))
                .retrieveFlux(Coffee.class);
    }
}
