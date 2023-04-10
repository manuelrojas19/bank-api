package com.manuelr.bank.api.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import java.util.Objects;
import java.util.function.Function;

public final class RequestUtils {

  private RequestUtils() {
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION_EXCEPTION_MSG);
  }

  public static PageRequest getPageRequestFromRequest(ServerRequest request) {

    final var page = getQueryParam(request, Constants.PAGE_HEADER, Integer::parseInt);
    final var size = getQueryParam(request, Constants.SIZE_HEADER, Integer::parseInt);

    return PageRequest.of(
        Objects.nonNull(page) ? page : Constants.DEFAULT_PAGE,
        Objects.nonNull(size) ? size : Constants.DEFAULT_SIZE);
  }

  public static <T> T getQueryParam(
      ServerRequest request, String queryParam, Function<String, T> queryResolver) {
    if (request.queryParam(queryParam).isEmpty()) {
      return null;
    }
    return queryResolver.apply(request.queryParam(queryParam).get());
  }
}
