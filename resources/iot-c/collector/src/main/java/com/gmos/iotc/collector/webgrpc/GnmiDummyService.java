package com.gmos.iotc.collector.webgrpc;

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
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@GrpcService
public class GnmiDummyService extends gNMIGrpc.gNMIImplBase {

  private Logger logger = LoggerFactory.getLogger(GnmiDummyService.class);


  public GnmiDummyService() {

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
                .setVal(TypedValue.newBuilder().setAsciiVal("test-val").build())
                . build();
        Notification notification = Notification.newBuilder().addUpdate(update).build();


        SubscribeResponse response = SubscribeResponse.newBuilder()
                .mergeUpdate(notification)
                .build();

        while (true){
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
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
}

