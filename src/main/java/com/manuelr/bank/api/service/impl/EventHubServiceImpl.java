package com.manuelr.bank.api.service.impl;

import com.manuelr.bank.api.service.EventHubService;
import com.manuelr.bank.api.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
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
    Mono.fromCallable(
            () -> {
              log.info(Constants.RECEIVED_HEADERS_LOG_MSG, headers);
              return sqsClient.sendMessage(
                  sendMessageRequest ->
                      sendMessageRequest
                          .queueUrl(queueName)
                          .messageBody(headers.toString())
                          .build());
            })
        .subscribeOn(Schedulers.boundedElastic())
        .doOnSuccess(
            sendMessageResponse ->
                log.info(Constants.EVENT_HUB_MESSAGE_SENT_LOG_MSG, sendMessageResponse.messageId()))
        .doOnError(throwable -> log.error(Constants.EVENT_HUB_ERROR_LOG_MSG, throwable))
        .subscribe();
  }
}
