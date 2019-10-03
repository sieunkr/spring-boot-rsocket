package com.example.demo;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.util.DefaultPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeController {

    private final RSocket rSocket;

    @GetMapping("/request-response")
    public Mono<Payload> getRequestResponse(){

        return rSocket.requestResponse(DefaultPayload.create("Hello World!"));
    }

    @GetMapping("/fire-and-forget")
    public Mono<Void> fireAndForget(){

        rSocket.fireAndForget(DefaultPayload.create("Hello World!"))
                .subscribe();

        return Mono.empty();

    }

    @GetMapping("/request-stream")
    public Flux<Payload> getRequestStream(){

        return rSocket.requestStream(DefaultPayload.create("Hello World!"));
    }

    @GetMapping("/channel")
    public Flux<Payload> getChannel(){

        return null;
    }

}
