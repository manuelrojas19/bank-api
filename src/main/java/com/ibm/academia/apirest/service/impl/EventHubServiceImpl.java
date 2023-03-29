package com.ibm.academia.apirest.service.impl;

import com.ibm.academia.apirest.service.EventHubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import software.amazon.awssdk.services.sqs.SqsClient;

@Slf4j
@Service
public class EventHubServiceImpl implements EventHubService {

  private final SqsClient sqsClient;

  private final String queueName;

  public EventHubServiceImpl(
      @Autowired SqsClient sqsClient, @Value("${bank-api.sqs.queue.name}") String queueName) {
    this.queueName = queueName;
    this.sqsClient = sqsClient;
  }

  public final void sendHeadersToEventHub(MultiValueMap<String, String> headers) {

    log.debug("Received headers --> {}", headers);

    sqsClient.sendMessage(
        sendMessageRequest ->
            sendMessageRequest.queueUrl(queueName).messageBody(headers.toString()).build());
  }
}
