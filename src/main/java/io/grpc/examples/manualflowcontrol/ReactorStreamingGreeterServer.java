package io.grpc.examples.manualflowcontrol;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.logging.Logger;

public class ReactorStreamingGreeterServer {

    private static final Logger logger =
            Logger.getLogger(ReactorStreamingGreeterServer.class.getName());

    public static void main(String[] args) throws InterruptedException, IOException {
        final ReactorStreamingGreeterGrpc.StreamingGreeterImplBase svc = new ReactorStreamingGreeterGrpc.StreamingGreeterImplBase() {

            @Override
            public Flux<HelloReply> sayHelloStreaming(Flux<HelloRequest> request) {
                return request.map(ReactorStreamingGreeterServer::respond);
            }
        };

        final Server server = ServerBuilder
                .forPort(50051)
                .addService(svc)
                .build()
                .start();

        logger.info("Listening on " + server.getPort());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down");
            server.shutdown();
        }));
        server.awaitTermination();
    }

    private static HelloReply respond(HelloRequest req) {
        return HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
    }
}
