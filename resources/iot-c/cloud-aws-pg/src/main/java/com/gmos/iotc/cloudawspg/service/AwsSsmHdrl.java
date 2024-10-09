package com.gmos.iotc.cloudawspg.service;

import com.gmos.iotc.cloudawspg.config.IoTConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AwsSsmHdrl {
    private final IoTConfig ioTConfig;

    @Value("${kafka.bs}")
    private String kafkaBs;


//    @Value("${cloud-aws-pg.key1}")
//    private String cloudAwsPgKey1;

    public AwsSsmHdrl(IoTConfig ioTConfig) {
        this.ioTConfig = ioTConfig;
        testAwsSsm();
    }

    public String testAwsSsm(){
        String result = "";
        //concatenate format string with ioTConfig.getKey1() and kafkaBs
        result = String.format("cloudAwsPgKey1: %s\n, kafkaBs: %s", ioTConfig.getKey1(), kafkaBs);
        System.out.println("cloudAwsPgKey1: "+ ioTConfig.getKey1());
        System.out.println("kafkaBs: "+ kafkaBs);
        return result;
    }
}