package com.example.demo;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SimpleRSocketImpl extends AbstractRSocket {

    @Override
    public Mono<Void> fireAndForget(Payload payload){

        System.out.println("dd");

        return Mono.empty();
    }

    @Override
    public Flux<Payload> requestStream(Payload payload) {

        //return service.findAll()
        //        .map(mqttMessageEntity -> DefaultPayload.create(mqttMessageEntity.toString()));
        return Flux.just(DefaultPayload.create("1"));
    }

}