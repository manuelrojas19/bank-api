package com.manuelr.bank.api.handler;

import com.manuelr.bank.api.model.FindBankRequestFilterData;
import com.manuelr.bank.api.service.BankService;
import com.manuelr.bank.api.service.EventHubService;
import com.manuelr.bank.api.utils.Constants;
import com.manuelr.bank.api.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class FindBankHandler implements HandlerFunction<ServerResponse> {

  private final BankService bankService;

  private final EventHubService eventHubService;

  @Autowired
  public FindBankHandler(BankService bankService, EventHubService eventHubService) {
    this.bankService = bankService;
    this.eventHubService = eventHubService;
  }

  @NonNull
  @Override
  public Mono<ServerResponse> handle(@NonNull ServerRequest request) {

    log.info(Constants.REQUEST_RECEIVED_LOG_MSG, FindBankHandler.class.getName());

    final FindBankRequestFilterData requestData = getFindBankRequestData(request);

    return bankService
        .findBanks(requestData)
        .flatMap(findBankResponse -> ServerResponse.ok().bodyValue(findBankResponse))
        .doOnTerminate(() -> eventHubService.sendHeadersToEventHub(requestData.getHeaders()))
        .doOnNext(response -> log.info(Constants.SENDING_RESPONSE_LOG_MSG, response))
        .doOnError(throwable -> log.error(Constants.BANK_INFO_ERROR_LOG_MSG, throwable));
  }

  private static FindBankRequestFilterData getFindBankRequestData(ServerRequest request) {
    final var pageable = RequestUtils.getPageRequestFromRequest(request);
    final var headers = request.headers().asHttpHeaders();

    final var latitude =
        RequestUtils.getQueryParam(request, Constants.LATITUDE_QUERY_PARAM, Double::valueOf);
    final var longitude =
        RequestUtils.getQueryParam(request, Constants.LONGITUDE_QUERY_PARAM, Double::valueOf);
    final var postalCode =
        RequestUtils.getQueryParam(request, Constants.POSTAL_CODE_QUERY_PARAM, String::toString);
    final var state =
        RequestUtils.getQueryParam(request, Constants.STATE_QUERY_PARAM, String::toString);
    final var address =
        RequestUtils.getQueryParam(request, Constants.ADDRESS_QUERY_PARAM, String::toString);

    return FindBankRequestFilterData.builder()
        .latitude(latitude)
        .longitude(longitude)
        .postalCode(postalCode)
        .state(state)
        .address(address)
        .pageable(pageable)
        .headers(headers)
        .build();
  }
}
