package com.gmos.iotc.cloudawspg.service;

import com.gmos.iotc.cloudawspg.config.IoTConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AwsSsmHdrl {
    private final IoTConfig ioTConfig;

    @Value("${kafka.bs}")
    private String kafkaBs;

    public AwsSsmHdrl(IoTConfig ioTConfig) {
        this.ioTConfig = ioTConfig;
        testAwsSsm();
    }

    public String testAwsSsm(){
        System.out.println("kafkaBs: "+ kafkaBs);
        return "done";
    }
}