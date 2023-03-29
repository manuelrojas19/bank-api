package com.ibm.academia.apirest.service;

import com.ibm.academia.apirest.model.FindBankResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

public interface BankService {

  Mono<FindBankResponse> findBanks(
      Pageable pageable,
      Double latitude,
      Double longitude,
      String postalCode,
      String state,
      String address,
      MultiValueMap<String, String> headers);
}
