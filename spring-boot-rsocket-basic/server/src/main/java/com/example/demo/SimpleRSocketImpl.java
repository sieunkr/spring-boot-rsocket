package com.example.demo;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class SimpleRSocketImpl extends AbstractRSocket {

    @Override
    public Mono<Void> fireAndForget(Payload payload){

        System.out.println("server received");

        return Mono.empty();
    }

    @Override
    public Mono<Payload> requestResponse(Payload payload) {

        log.info(payload.getDataUtf8());

        return Mono.just(DefaultPayload.create("단 하나의 데이터"));
    }

    @Override
    public Flux<Payload> requestStream(Payload payload) {

        log.info(payload.getDataUtf8());

        return Flux.just(
                DefaultPayload.create("첫번째 데이터 스트림"),
                DefaultPayload.create("두번째 데이터 스트림"));
    }

    @Override
    public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
        log.info("테스트");
        return null;
    }

}

