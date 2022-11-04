package com.gmos.iotc.collector.service;

import com.github.gnmi.proto.Encoding;
import com.github.gnmi.proto.Path;
import com.github.gnmi.proto.PathElem;
import com.github.gnmi.proto.SubscribeRequest;
import com.github.gnmi.proto.SubscribeResponse;
import com.github.gnmi.proto.Subscription;
import com.github.gnmi.proto.SubscriptionList;
import com.github.gnmi.proto.SubscriptionMode;
import com.github.gnmi.proto.gNMIGrpc;
import com.github.gnmi.proto.gNMIGrpc.gNMIStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GnmiGrpcClientWorker {

  private gNMIStub stub;
  private ManagedChannel channel;
  private String ne;
  private Logger logger;

  public GnmiGrpcClientWorker(String ne) {
    String[] parts = ne.split(":");
    String ip = parts[0];
    int port = Integer.parseInt(parts[1]) ;
    this.ne = ne;
    this.logger = LoggerFactory.getLogger(GnmiGrpcClientWorker.class);
    this.channel = ManagedChannelBuilder.forAddress(ip, port).usePlaintext().build();
    this.stub = gNMIGrpc.newStub(channel);
  }

  public String getDataOverGnmiSubscribeStream(){
//    logger.info(ne + " getDataOverGnmiSubscribeStream()" );
    System.out.println(ne + " getDataOverGnmiSubscribeStream()" );
    // Latch is needed otherwise onNext is never reached
    CountDownLatch latch = new CountDownLatch(1);

    StreamObserver<SubscribeRequest> stream = stub.subscribe(new StreamObserver<SubscribeResponse>() {
       @Override
       public void onNext(SubscribeResponse response) {
//         String clockClass =  value.getUpdate().getUpdateList().get(1).getVal().toString();
//         System.out.println("clockClass "+clockClass);

         try {
           String thread = Thread.currentThread().getName();
           String responseVal0 =  response.getUpdate().getUpdateList().get(0).getVal().toString();
           String msg = ne + " onNext() - thread: " +thread + " responseVal0: " + responseVal0;
           System.out.println(msg);
//           System.out.println(value.toString());

           // NOTE logger is NOT working
//           logger.info("{} onNext", ne);
           System.out.println("------------------------");
         }catch (Exception e){
           System.out.println("onNext Error: "+e.getMessage());
         }
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

    SubscriptionList list = SubscriptionList.newBuilder()
            .addSubscription(subscription)
            .addSubscription(getSubscriptionForClockClass())
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
  private Subscription getSubscriptionForClockClass(){
//    name = "ssync_ClockClass"
//    origin = "openconfig"
//    path = "/ptp/instance-list/1/default-ds/clock-quality/clock-class"
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
            .setName("default-ds")
            .build();
    PathElem cq = PathElem.newBuilder()
            .setName("clock-quality")
            .build();
    PathElem cc = PathElem.newBuilder()
            .setName("clock-class")
            .build();

    Path path = Path.newBuilder()
            .addElem(ptp)
            .addElem(il)
            .addElem(one)
            .addElem(cds)
            .addElem(cq)
            .addElem(cc)
            .setOrigin("openconfig")
            .setTarget("ssync_ClockClass")
            .build();
    Subscription subscription = Subscription.newBuilder()
            .setPath(path)
            .setMode(SubscriptionMode.SAMPLE)
            .setSampleInterval(3000000000l) //ns 1000000000l => 1s
            .build();
    return subscription;
  }
}
