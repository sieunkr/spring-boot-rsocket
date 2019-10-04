package com.example.demo;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CafeRepository {

    private Map<String, Coffee> coffeeMap;

    @PostConstruct
    public void init(){
        coffeeMap = new HashMap<>();
        coffeeMap.put("latte", Coffee.builder().name("latte").price(1200).build());
        coffeeMap.put("mocha", Coffee.builder().name("mocha").price(1500).build());
    }

    public Mono<Coffee> findByName(String name){

        Assert.notNull(name, "name must not be null");

        return Mono.just(coffeeMap.get(name));
    }

    public Flux<Coffee> findAll(){
        return Flux.fromIterable(coffeeMap.values());
    }

    public Mono<Void> add(RequestCoffee coffee){
        coffeeMap.put(coffee.getName(), Coffee.builder().name(coffee.getName()).build());
        return Mono.empty();
    }
}