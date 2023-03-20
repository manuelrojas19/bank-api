package com.ibm.academia.apirest.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClient {

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }
}
