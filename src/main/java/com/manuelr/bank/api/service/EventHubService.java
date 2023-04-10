package com.manuelr.bank.api.service;

import org.springframework.util.MultiValueMap;

public interface EventHubService {

  void sendHeadersToEventHub(MultiValueMap<String, String> headers);
}
