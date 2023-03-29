package com.ibm.academia.apirest.service;

import org.springframework.util.MultiValueMap;

public interface EventHubService {

  void sendHeadersToEventHub(MultiValueMap<String, String> headers);
}
