package com.example.demo;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RSocketConfig {

    @Bean
    public RSocket rSocket(){
        return RSocketFactory.connect()
                        .transport(TcpClientTransport.create("localhost", 1234))
                        .start()
                        .block();
    }
}
