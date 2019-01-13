# Reactor GRPC Examples

This project demonstrates how to use [gRPC-Java](https://github.com/grpc/grpc-java) with 
[reactive-grpc](https://github.com/salesforce/reactive-grpc) in order to greatly simplify the development of gRPC 
clients and servers and avoid common pitfalls such as ignoring back-pressure.

The project contains two Protobuf examples, [`helloworld.proto`](./blob/master/src/main/proto/helloworld.proto) and 
[`hello_streaming.proto`](./blob/master/src/main/proto/hello_streaming.proto).
The examples have been forked from the original gRPC-Java repository, along with their client and server classes. 
The client and server classes have then been copied and changed to use the 
[Reactor Core](https://github.com/reactor/reactor-core) API, so that you can see the effect on the code side-by-side.
The following table shows the original and modified classes. 

<table>
    <tr>
        <th>gRPC-Java</th>
        <th>reactive-grpc</th>
    </tr>
    <tr>
        <td><a href="blob/master/src/main/java/io/grpc/examples/helloworld/HelloWorldClient.java">HelloWorldClient</a></td>
        <td><a href="blob/master/src/main/java/io/grpc/examples/helloworld/ReactorGreeterClient.java">ReactorGreeterClient</a></td>
    </tr>
    <tr>
        <td><a href="blob/master/src/main/java/io/grpc/examples/helloworld/HelloWorldServer.java">HelloWorldServer</a></td>
        <td><a href="blob/master/src/main/java/io/grpc/examples/helloworld/ReactorGreeterServer.java">ReactorGreeterServer</a></td>
    </tr>
    <tr>
        <td><a href="blob/master/src/main/java/io/grpc/examples/manualflowcontrol/ManualFlowControlClient.java">ManualFlowControlClient</a></td>
        <td><a href="blob/master/src/main/java/io/grpc/examples/manualflowcontrol/ReactorStreamingGreeterClient.java">ReactorStreamingGreeterClient</a></td>
    </tr>
    <tr>
        <td><a href="blob/master/src/main/java/io/grpc/examples/manualflowcontrol/ManualFlowControlServer.java">ManualFlowControlServer</a></td>
        <td><a href="blob/master/src/main/java/io/grpc/examples/manualflowcontrol/ReactorStreamingGreeterServer.java">ReactorStreamingGreeterServer</a></td>
    </tr>
</table>

## Copyright and License

Like the original code in the gRPC-Java repository, this project is covered by the Apache License, Version 2.0.
