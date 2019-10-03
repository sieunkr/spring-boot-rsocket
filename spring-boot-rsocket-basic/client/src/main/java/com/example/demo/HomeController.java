package com.example.demo;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
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

    @GetMapping("/test")
    public Mono<Void> getAll(){

        rSocket.fireAndForget(DefaultPayload.create("Hello World!"))
                .subscribe();

        return Mono.empty();
    }


}
