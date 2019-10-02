import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import java.time.Duration;
import java.time.Instant;
import org.reactivestreams.Publisher;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ChannelApplication {
    public static void main(String[] args) {
        final int port = 1234;

        /*
        1.SERVER
        */
        RSocket rSocketImpl = new AbstractRSocket() {
            @Override
            public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
                return Flux.from(payloads)
                        .map(str -> DefaultPayload.create("channel received : " + str.getDataUtf8()));
            }
        };

        Disposable server = RSocketFactory.receive()
                .acceptor((setupPayload, reactiveSocket) -> Mono.just(rSocketImpl))
                .transport(TcpServerTransport.create("localhost", port))
                .start()
                .subscribe();

        System.out.println("TCP server start, port : " + port);


        /*
        2.CLIENT
        */
        RSocket socket =
                RSocketFactory.connect()
                        .transport(TcpClientTransport.create("localhost", port))
                        .start()
                        .block();

        System.out.println("TCP client start, port : " + port);

        socket
                .requestChannel(Flux.interval(Duration.ofSeconds(1))
                        .map(i -> DefaultPayload.create("Hello World -  " + Instant.now())))
                .map(Payload::getDataUtf8)
                .doOnNext(System.out::println)
                .take(10)
                .doFinally(signalType -> socket.dispose())
                .then()
                .block();

        server.dispose();
    }
}
