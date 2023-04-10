package com.manuelr.bank.api.service;

import com.manuelr.bank.api.model.FindBankRequestFilterData;
import com.manuelr.bank.api.model.FindBankResponse;
import reactor.core.publisher.Mono;

public interface BankService {

  Mono<FindBankResponse> findBanks(FindBankRequestFilterData data);
}
