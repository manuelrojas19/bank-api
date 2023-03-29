package com.ibm.academia.apirest.config;

import com.ibm.academia.apirest.handler.FindBankHandler;
import com.ibm.academia.apirest.handler.InfoHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

  private final FindBankHandler findBankHandler;

  private final InfoHandler infoHandler;

  private final String basePath;

  private final String findBanksApiPath;

  public RouterConfig(
      final FindBankHandler findBankHandler,
      final InfoHandler infoHandler,
      @Value("${bank-api.apis.base-path}") final String basePath,
      @Value("${bank-api.apis.find-banks-path}") final String findBanksApiPath) {
    this.findBankHandler = findBankHandler;
    this.infoHandler = infoHandler;
    this.basePath = basePath;
    this.findBanksApiPath = findBanksApiPath;
  }

  @Bean
  public RouterFunction<ServerResponse> routes() {
    return RouterFunctions.route(GET(basePath.concat(findBanksApiPath)), findBankHandler)
        .and(route(GET(basePath.concat("/info")), infoHandler));
  }
}
