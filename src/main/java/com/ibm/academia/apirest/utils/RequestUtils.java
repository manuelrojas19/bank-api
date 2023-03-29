package com.ibm.academia.apirest.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import java.util.Objects;
import java.util.function.Function;

public final class RequestUtils {

  private static final String PAGE_HEADER = "page";

  private static final String SIZE_HEADER = "size";

  private static final int DEFAULT_PAGE = 0;

  private static final int DEFAULT_SIZE = 20;

  private RequestUtils() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static PageRequest getPageRequest(ServerRequest request) {

    final var page = getQueryParam(request, PAGE_HEADER, Integer::parseInt);
    final var size = getQueryParam(request, SIZE_HEADER, Integer::parseInt);

    return PageRequest.of(
        Objects.nonNull(page) ? page : DEFAULT_PAGE, Objects.nonNull(size) ? size : DEFAULT_SIZE);
  }

  public static <T> T getQueryParam(
      ServerRequest request, String queryParam, Function<String, T> queryResolver) {
    if (request.queryParam(queryParam).isEmpty()) {
      return null;
    }
    return queryResolver.apply(request.queryParam(queryParam).get());
  }
}
