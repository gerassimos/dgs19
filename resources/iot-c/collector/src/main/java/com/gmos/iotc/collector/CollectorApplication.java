package com.gmos.iotc.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollectorApplication.class, args);
	}

}
