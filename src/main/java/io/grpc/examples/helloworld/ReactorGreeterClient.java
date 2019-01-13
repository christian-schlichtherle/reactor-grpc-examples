/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.grpc.examples.helloworld;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * A simple client that requests a greeting from the {@link HelloWorldServer}.
 */
public class ReactorGreeterClient {

    private final ManagedChannel channel;
    private final ReactorGreeterGrpc.ReactorGreeterStub stub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public ReactorGreeterClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build());
    }

    /**
     * Construct client for accessing HelloWorld server using the existing channel.
     */
    ReactorGreeterClient(ManagedChannel channel) {
        this.channel = channel;
        stub = ReactorGreeterGrpc.newReactorStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Say hello to server.
     */
    public void greet(final String name) {
        final Mono<HelloRequest> request = Mono
                .just(HelloRequest.newBuilder().setName(name).build())
                .log(); // DON'T do this in production!
        stub.sayHello(request)
                .log() // DON'T do this in production!
                .block(Duration.of(1, ChronoUnit.SECONDS));
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        ReactorGreeterClient client = new ReactorGreeterClient("localhost", 50051);
        try {
            /* Access a service running on the local machine on port 50051 */
            String user = "world";
            if (args.length > 0) {
                user = args[0]; /* Use the arg as the name to greet if provided */
            }
            client.greet(user);
        } finally {
            client.shutdown();
        }
    }
}
