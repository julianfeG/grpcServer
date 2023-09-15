import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class HelloWorldServiceImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase{
    @Override
    public void hello(Hello.HelloRequest request, StreamObserver<Hello.HelloResponse> responseObserver) {
        System.out.println(
                "Handling hello endpoint: " + request.toString());


        String text = request.getText() + " World";
        Hello.HelloResponse response =
                Hello.HelloResponse.newBuilder()
                        .setText(text).build();
        String target = "192.168.100.6:8900";
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
            SimpleClient client = new SimpleClient(channel);
            client.generateNumber();
        } finally {
            try {
                channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
