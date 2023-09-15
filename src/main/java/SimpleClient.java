import co.com.bancodebogota.number.generation.grpc.GeneratedNumbersRequest;
import co.com.bancodebogota.number.generation.grpc.GeneratedNumbersResponse;
import co.com.bancodebogota.number.generation.grpc.NumberGenerationGrpc;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;

import java.util.UUID;

public class SimpleClient {
    private final NumberGenerationGrpc.NumberGenerationBlockingStub blockingStub;


    public SimpleClient(Channel channel) {
        blockingStub = NumberGenerationGrpc.newBlockingStub(channel);
    }

    public void generateNumber() {
        System.out.println("Generating number");
        UUID uuid = UUID.randomUUID();
        GeneratedNumbersRequest rq = GeneratedNumbersRequest.newBuilder()
                .setRequestId(uuid.toString()).setMinRange(0).setMaxRange(10).build();
        GeneratedNumbersResponse rs;
        try {
            rs = blockingStub.generatedNumbers(rq);
            System.out.println("Result success: "+ rs.getRandomWithNextInt());
        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed: {0}" + e.getStatus());
            return;
        }
    }
}
