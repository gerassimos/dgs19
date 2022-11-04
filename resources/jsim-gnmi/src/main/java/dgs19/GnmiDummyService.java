package dgs19;

import com.github.gnmi.proto.CapabilityRequest;
import com.github.gnmi.proto.CapabilityResponse;
import com.github.gnmi.proto.GetRequest;
import com.github.gnmi.proto.GetResponse;
import com.github.gnmi.proto.Notification;
import com.github.gnmi.proto.SetRequest;
import com.github.gnmi.proto.SetResponse;
import com.github.gnmi.proto.SubscribeRequest;
import com.github.gnmi.proto.SubscribeResponse;
import com.github.gnmi.proto.Subscription;
import com.github.gnmi.proto.TypedValue;
import com.github.gnmi.proto.Update;
import com.github.gnmi.proto.gNMIGrpc;
import io.grpc.stub.StreamObserver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GnmiDummyService extends gNMIGrpc.gNMIImplBase {

  private final Logger logger;

  public GnmiDummyService() {
    logger = Logger.getLogger( this.getClass().getName() );
  }

  @Override
  public void capabilities(CapabilityRequest request, StreamObserver<CapabilityResponse> responseObserver) {
    super.capabilities(request, responseObserver);
  }

  @Override
  public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
    super.get(request, responseObserver);
  }

  @Override
  public void set(SetRequest request, StreamObserver<SetResponse> responseObserver) {
    super.set(request, responseObserver);
  }

  @Override
  public StreamObserver<SubscribeRequest> subscribe(StreamObserver<SubscribeResponse> responseObserver) {
    return new StreamObserver<SubscribeRequest>() {
      @Override
      public void onNext(SubscribeRequest request) {
        Subscription subscription = request.getSubscribe().getSubscription(0);

        Update update = Update.newBuilder().setPath(subscription.getPath())
                .setVal(TypedValue.newBuilder().setAsciiVal(getHostname() + " test-dummy-val").build())
                . build();
        Notification notification = Notification.newBuilder().addUpdate(update).build();


        SubscribeResponse response = SubscribeResponse.newBuilder()
                .mergeUpdate(notification)
                .build();
        int count = 0;
        while (true){
          count++;
          try {
            TimeUnit.SECONDS.sleep(3);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          logger.info("subscribe onNext: "+count);
          responseObserver.onNext(response);
        }

      }

      @Override
      public void onError(Throwable t) {
        System.out.println("subscribe error: "+t.getMessage());
        responseObserver.onError(t);
      }

      @Override
      public void onCompleted() {
        System.out.println("subscribe onCompleted");
        responseObserver.onCompleted();
      }
    };
  }

  private String getHostname(){
    String result = "UnknownHost";
    try {
      String  port = System.getenv().getOrDefault("GRPC_SERVER_PORT", "4567");
      result = InetAddress.getLocalHost().getHostName()+":"+port;

    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return result;
  }
}

