import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RequestStreamApplication {

    public static void main(String[] args) throws Exception {

        final int port = 1234;

        /*
        1.SERVER
        */

        RSocket rSocketImpl = new AbstractRSocket() {
            @Override
            public Flux<Payload> requestStream(Payload payload) {

                System.out.println(payload.getDataUtf8());

                return Flux.range(1, 10)
                        .map(i -> DefaultPayload.create("onNext-" + i));
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

        socket.requestStream(DefaultPayload.create("Hello World!"))
                .subscribe(
                        payload -> System.out.println(payload.getDataUtf8()),
                        e -> System.out.println("error" + e.toString()),
                        () -> System.out.println("completed")
                );

        Thread.sleep(3000);
        socket.dispose();
        server.dispose();
    }

}