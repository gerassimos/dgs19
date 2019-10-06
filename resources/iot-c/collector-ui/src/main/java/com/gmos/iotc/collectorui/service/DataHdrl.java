package com.gmos.iotc.collectorui.service;

import com.gmos.iotc.common.PerformanceDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.time.Duration;
import java.util.List;

@Component
public class DataHdrl {

  private final RestTemplate restTemplate;
  private Logger logger = LoggerFactory.getLogger(DataHdrl.class);

  public DataHdrl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(10)).build();
  }

  public void getData(long deviceId){
    logger.debug("getData for deviceId: "+ deviceId);
    //Ref :
    //https://www.baeldung.com/spring-rest-template-list
    //This sends a request to the specified URI using the GET verb and converts the response body
    // into the requested Java type. This works great for most classes, but it has a limitation:
    // we cannot send lists of objects.
//    String url = "http://localhost:8092/device/performance?deviceId=1";
//    String jsonStringForSatellitePosition = restTemplate.getForObject(url, String.class);
//    logger.debug("jsonString " + jsonStringForSatellitePosition);

    //Ref :
    //https://www.baeldung.com/spring-rest-template-list
    //3.1. Using ParameterizedTypeReference
    String urlStr = UriComponentsBuilder.newInstance().
            scheme("http").host("localhost" ).port(8092).path("/device/performance").
            queryParam("deviceId", deviceId).
        build().toUriString();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    ResponseEntity<List<PerformanceDataDTO>> response = restTemplate.exchange(
            urlStr,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<PerformanceDataDTO>>(){});
    List<PerformanceDataDTO> performanceDataDTOList = response.getBody();
    for (PerformanceDataDTO performanceDataDTO :performanceDataDTOList){
      System.out.println("performanceDataDTO: " + performanceDataDTO.toString());
    }
  }
}
