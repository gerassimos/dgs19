package com.gmos.iotc.collectorui.service;

import com.github.gnmi.proto.Encoding;
import com.github.gnmi.proto.Path;
import com.github.gnmi.proto.PathElem;
import com.github.gnmi.proto.SubscribeRequest;
import com.github.gnmi.proto.Subscription;
import com.github.gnmi.proto.SubscriptionList;
import com.github.gnmi.proto.SubscriptionMode;
import com.github.gnmi.proto.gNMIGrpc.gNMIStub;
import com.github.gnmi.proto.SubscribeResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class GnmiHdrlGrpcClient {

  @GrpcClient("gnmi-grpc-server")
  private gNMIStub stub;

  public String doGnmi(){
    System.out.println("Enter doGnmi");
    CountDownLatch latch = new CountDownLatch(1);
//    GreetingResponse response = stub.greeting(GreetingRequest.newBuilder().setName(name).build());
    StreamObserver<SubscribeRequest> stream = stub.subscribe(new StreamObserver<SubscribeResponse>() {
       @Override
       public void onNext(SubscribeResponse value) {
         System.out.println("SubscribeResponse value" +value.toString());
       }

       @Override
       public void onError(Throwable t) {
         System.out.println("StreamObserver onError " + t.toString());
       }

       @Override
       public void onCompleted() {
         System.out.println("StreamObserver onCompleted");
         latch.countDown();
       }
     }
    );

//    PathElem pathElem = PathElem.newBuilder()
//            .setName("ptp/instance-list/1/current-ds/offset-from-master")
//            .build();
    PathElem ptp = PathElem.newBuilder()
            .setName("ptp")
            .build();
    PathElem il = PathElem.newBuilder()
            .setName("instance-list")
            .build();
    PathElem one = PathElem.newBuilder()
            .setName("1")
            .build();
    PathElem cds = PathElem.newBuilder()
            .setName("current-ds")
            .build();
    PathElem ofm = PathElem.newBuilder()
            .setName("offset-from-master")
            .build();

    Path path = Path.newBuilder()
            .addElem(ptp)
            .addElem(il)
            .addElem(one)
            .addElem(cds)
            .addElem(ofm)
            .setOrigin("openconfig")
            .setTarget("ssync_OFM")
            .build();
    Subscription subscription = Subscription.newBuilder()
            .setPath(path)
            .setMode(SubscriptionMode.SAMPLE)
            .setSampleInterval(3000000000l) //ns 1000000000l => 1s
            .build();

    SubscriptionList list = SubscriptionList.newBuilder().addSubscription(subscription)
            .setEncoding(Encoding.JSON)
            .build();
    SubscribeRequest request =SubscribeRequest.newBuilder().setSubscribe(list).build();
    stream.onNext(request);
    stream.onCompleted();
    try {
      latch.await(3, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println("InterruptedException latch.await "+e.getMessage());
      throw new RuntimeException(e);
    }
    return "doGnmi";
  }

}
