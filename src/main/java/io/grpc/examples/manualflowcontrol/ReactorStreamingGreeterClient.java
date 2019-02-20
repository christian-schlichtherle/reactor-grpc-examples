package io.grpc.examples.manualflowcontrol;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReactorStreamingGreeterClient {

    public static void main(String[] args) throws InterruptedException {
        // Create a channel and a stub
        final ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        final ReactorStreamingGreeterGrpc.ReactorStreamingGreeterStub stub
                = ReactorStreamingGreeterGrpc.newReactorStub(channel);

        // Process request and response streams.
        final Flux<HelloRequest> requests = Flux
                .fromIterable(names())
                .map(name -> HelloRequest.newBuilder().setName(name).build())
                ;//.log(); // DON'T do this in production!

        final long startTime = System.nanoTime();
        stub.sayHelloStreaming(requests)
                .log() // DON'T do this in production!
                .blockLast(Duration.ofSeconds(12)); // DON'T do this in production!
        final long endTime = System.nanoTime();
        System.err.println("Duration: " + Duration.ofNanos(endTime - startTime));

        channel.shutdown()
                .awaitTermination(1, TimeUnit.SECONDS);
    }

    private static List<String> names() {
        return Arrays.asList(
                "Sophia",
                "Jackson",
                "Emma",
                "Aiden",
                "Olivia",
                "Lucas",
                "Ava",
                "Liam",
                "Mia",
                "Noah",
                "Isabella",
                "Ethan",
                "Riley",
                "Mason",
                "Aria",
                "Caden",
                "Zoe",
                "Oliver",
                "Charlotte",
                "Elijah",
                "Lily",
                "Grayson",
                "Layla",
                "Jacob",
                "Amelia",
                "Michael",
                "Emily",
                "Benjamin",
                "Madelyn",
                "Carter",
                "Aubrey",
                "James",
                "Adalyn",
                "Jayden",
                "Madison",
                "Logan",
                "Chloe",
                "Alexander",
                "Harper",
                "Caleb",
                "Abigail",
                "Ryan",
                "Aaliyah",
                "Luke",
                "Avery",
                "Daniel",
                "Evelyn",
                "Jack",
                "Kaylee",
                "William",
                "Ella",
                "Owen",
                "Ellie",
                "Gabriel",
                "Scarlett",
                "Matthew",
                "Arianna",
                "Connor",
                "Hailey",
                "Jayce",
                "Nora",
                "Isaac",
                "Addison",
                "Sebastian",
                "Brooklyn",
                "Henry",
                "Hannah",
                "Muhammad",
                "Mila",
                "Cameron",
                "Leah",
                "Wyatt",
                "Elizabeth",
                "Dylan",
                "Sarah",
                "Nathan",
                "Eliana",
                "Nicholas",
                "Mackenzie",
                "Julian",
                "Peyton",
                "Eli",
                "Maria",
                "Levi",
                "Grace",
                "Isaiah",
                "Adeline",
                "Landon",
                "Elena",
                "David",
                "Anna",
                "Christian",
                "Victoria",
                "Andrew",
                "Camilla",
                "Brayden",
                "Lillian",
                "John",
                "Natalie",
                "Lincoln"
        );
    }
}
