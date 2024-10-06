package com.gmos.iotc.cloudawspg.web;

import com.gmos.iotc.cloudawspg.service.AwsSsmHdrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  private Logger logger = LoggerFactory.getLogger(Controller.class);
  private final AwsSsmHdrl awsSsmHdrl;


  public Controller(AwsSsmHdrl awsSsmHdrl) {
    this.awsSsmHdrl = awsSsmHdrl;
  }

  @GetMapping("/test-aws-ssm")
  public String testAwsSsm() {
    return awsSsmHdrl.testAwsSsm();
  }

}

