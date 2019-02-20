/*
 * Copyright 2017 The gRPC Authors
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

package io.grpc.examples.manualflowcontrol;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import jep.Jep;
import jep.JepException;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.logging.Logger;

public class ReactorPythonStreamingGreeterServer {

    private static final Logger logger =
            Logger.getLogger(ReactorPythonStreamingGreeterServer.class.getName());

    public static void main(String[] args) throws InterruptedException, IOException {
        final ReactorStreamingGreeterGrpc.StreamingGreeterImplBase svc = new ReactorStreamingGreeterGrpc.StreamingGreeterImplBase() {

            @Override
            public Flux<HelloReply> sayHelloStreaming(Flux<HelloRequest> request) {
                return request.map(ReactorPythonStreamingGreeterServer::respond);
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

    private static final ThreadLocal<Jep> threadLocalJep = new ThreadLocal<Jep>() {

        @Override
        protected Jep initialValue() {
            logger.info("Creating Jep for " + Thread.currentThread());
            try {
                return new Jep();
            } catch (JepException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private static HelloReply respond(final HelloRequest req) {
        final Jep jep = threadLocalJep.get();
        try {
            jep.set("name", req.getName());
            final String message = jep.getValue("'Hello ' + name", String.class);
            return HelloReply.newBuilder().setMessage(message).build();
        } catch (JepException e) {
            throw new RuntimeException(e);
        }
    }
}
