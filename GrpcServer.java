package user;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting gRPC Server");

        Server server = ServerBuilder.forPort(12345).addService(
                new UserService()).build();

        server.start();
        System.out.println("server started at "+server.getPort());
        server.awaitTermination();
    }
}
