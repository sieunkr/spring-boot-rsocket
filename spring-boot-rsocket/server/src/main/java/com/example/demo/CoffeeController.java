package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CoffeeController {

    private final CafeRepository cafeRepository;

    public CoffeeController(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    @MessageMapping("request-response")
    Mono<Coffee> getCoffeeByName(RequestCoffee request){
        return cafeRepository.findByName(request.getName());
    }

    @MessageMapping("request-stream")
    Flux<Coffee> getAllCoffee(){
        return cafeRepository.findAll();
    }

}