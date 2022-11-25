package com.gmos.iotc.collector.web;

import com.gmos.iotc.collector.service.gnmi.GnmiPathBuilder;
import com.gmos.iotc.collector.service.gnmi.GrpcClientTester;
import com.gmos.iotc.common.gnmi.SubscriptionListDTO;
import com.gmos.iotc.common.gnmi.SubscriptionCfgDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GnmiTestRestController {

  private Logger logger = LoggerFactory.getLogger(GnmiTestRestController.class);
  private final GrpcClientTester grpcClientTester;

  public GnmiTestRestController(GrpcClientTester grpcClientTester) {
    this.grpcClientTester = grpcClientTester;
  }

  @GetMapping("/gnmi/test/{action}")
  public String gnmi(@PathVariable("action") String action) {
    logger.debug("gnmi action: {}", action);
    switch (action){
      case "status":
        grpcClientTester.getConnectionStatesFromAllGrpcClients();
        break;
      case "add-subscription":
        grpcClientTester.addSubscription();
        break;
      case "cancel-streaming":
        grpcClientTester.cancelStreaming();
        break;
      default:
        System.err.println("Action not supported");
    }

    String result = "gnmi action: " + action;
    return result;
  }

  @GetMapping("/gnmi/example/dto/subscriptionlist")
  public SubscriptionListDTO getExampleSubListDTO(){
    return GnmiPathBuilder.buildExampleSubscriptionListDTO4OFM();
  }

  @GetMapping("/gnmi/example/dto/subscription-operation")
  public SubscriptionCfgDTO getExampleSubOperDTO(){
    return GnmiPathBuilder.buildExampleSubscriptionOperationDTO4OFM();
  }

  @PostMapping("/gnmi/test/add/subscribe")
  public String subscribe(@RequestBody SubscriptionCfgDTO subscriptionCfgDTO){
    logger.info("subscribe for {}" , subscriptionCfgDTO.toString());
    grpcClientTester.subscribe(subscriptionCfgDTO);
    return "done";
  }
}
