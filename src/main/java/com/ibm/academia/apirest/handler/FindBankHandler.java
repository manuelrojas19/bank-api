package com.ibm.academia.apirest.handler;

import com.ibm.academia.apirest.model.FindBankResponse;
import com.ibm.academia.apirest.service.BankService;
import com.ibm.academia.apirest.utils.Constants;
import com.ibm.academia.apirest.utils.RequestUtils;
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

  @Autowired
  public FindBankHandler(BankService bankService) {
    this.bankService = bankService;
  }

  @NonNull
  @Override
  public Mono<ServerResponse> handle(@NonNull ServerRequest request) {

    log.info(Constants.REQUEST_RECEIVED_LOG_MSG, FindBankHandler.class.getName());

    final var pageable = RequestUtils.getPageRequest(request);
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

    return ServerResponse.ok()
        .body(
            bankService.findBanks(
                pageable, latitude, longitude, postalCode, state, address, headers),
            FindBankResponse.class)
        .doOnNext(
            response ->
                log.info(Constants.SENDING_RESPONSE_LOG_MSG, FindBankHandler.class.getName()));
  }
}
