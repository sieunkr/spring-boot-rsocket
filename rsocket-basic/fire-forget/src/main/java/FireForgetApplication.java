import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

public class FireForgetApplication {
    public static void main(String[] args) throws Exception {

        final int port = 1234;

        /*
        1.SERVER
        */

        RSocket rSocketImpl = new AbstractRSocket() {
            @Override
            public Mono<Void> fireAndForget(Payload payload) {
                System.out.println(payload.getDataUtf8());
                return Mono.empty();
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
                .fireAndForget(DefaultPayload.create("Hello World!"))
                .subscribe();

        Thread.sleep(3000);
        socket.dispose();
        server.dispose();
    }
}
