package com.ibm.academia.apirest.service;

import com.ibm.academia.apirest.model.FindBankFilterData;
import com.ibm.academia.apirest.model.FindBankResponse;
import reactor.core.publisher.Mono;

public interface BankService {

  Mono<FindBankResponse> findBanks(FindBankFilterData data);
}
