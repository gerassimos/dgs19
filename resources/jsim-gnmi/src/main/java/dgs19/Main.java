package dgs19;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Starting...");
    int port = Integer.parseInt(System.getenv().getOrDefault("GRPC_SERVER_PORT", "4567"));

    // plaintext server
    Server server = ServerBuilder
            .forPort(port)
            .addService(new GnmiDummyService())
            .build();
    server.start();
    System.out.println("grpc server started on port: "+port);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Received Shutdown Request");
      server.shutdown();
      System.out.println("Successfully stopped the server");
    }));

    server.awaitTermination();
  }

}
