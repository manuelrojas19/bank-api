package com.manuelr.bank.api.service.impl;

import com.manuelr.bank.api.entity.BankEntity;
import com.manuelr.bank.api.mapper.BankDataMapper;
import com.manuelr.bank.api.repository.BankRepository;
import com.manuelr.bank.api.service.BankService;
import com.manuelr.bank.api.utils.Constants;
import java.util.Objects;
import com.manuelr.bank.api.model.FindBankRequestFilterData;
import com.manuelr.bank.api.model.FindBankResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class BankServiceImpl implements BankService {

  private final BankRepository bankRepository;

  private final ReactiveHashOperations<String, Integer, FindBankResponse> hashOperations;

  @Autowired
  public BankServiceImpl(
      BankRepository bankRepository,
      ReactiveHashOperations<String, Integer, FindBankResponse> hashOperations) {
    this.bankRepository = bankRepository;
    this.hashOperations = hashOperations;
  }

  @Override
  public Mono<FindBankResponse> findBanks(FindBankRequestFilterData requestData) {
    return hashOperations
        .get(Constants.ATM_CACHE_NAME, requestData.hashCode())
        .switchIfEmpty(getFromDatabaseAndCache(requestData))
        .doOnNext(bank -> log.info(Constants.BANK_INFO_RETRIEVED_LOG_MSG, bank))
        .doOnError(error -> log.error(Constants.BANK_INFO_ERROR_LOG_MSG, error));
  }

  private Mono<FindBankResponse> getFromDatabaseAndCache(FindBankRequestFilterData requestData) {
    return getBankEntitiesByFilterData(requestData)
        .map(BankDataMapper::bankEntityToBankDto)
        .collectList()
        .zipWith(bankRepository.countAllBy())
        .map(
            tuples ->
                BankDataMapper.toFindBankResponse(
                    requestData.getPageable(), tuples.getT1(), tuples.getT2()))
        .flatMap(
            findBankResponse ->
                hashOperations
                    .put(Constants.ATM_CACHE_NAME, requestData.hashCode(), findBankResponse)
                    .thenReturn(findBankResponse));
  }

  private Flux<BankEntity> getBankEntitiesByFilterData(FindBankRequestFilterData requestData) {

    final var pageable = requestData.getPageable();
    final var latitude = requestData.getLatitude();
    final var longitude = requestData.getLongitude();
    final var postalCode = requestData.getPostalCode();
    final var state = requestData.getState();
    final var address = requestData.getAddress();

    if (Objects.nonNull(latitude) && Objects.nonNull(longitude)) {
      return bankRepository.findAllByLocation_LatitudeBetweenAndLocation_LongitudeBetween(
          latitude - Constants.LOCATION_MARGIN,
          latitude + Constants.LOCATION_MARGIN,
          longitude - Constants.LOCATION_MARGIN,
          longitude + Constants.LOCATION_MARGIN,
          pageable);
    } else if (Objects.nonNull(postalCode) && Objects.nonNull(state)) {
      return bankRepository.findAllByPostalCodeAndStateIgnoreCase(postalCode, state, pageable);
    } else if (Objects.nonNull(postalCode)) {
      return bankRepository.findAllByPostalCode(postalCode, pageable);
    } else if (Objects.nonNull(state)) {
      return bankRepository.findAllByStateIgnoreCase(state, pageable);
    } else if (Objects.nonNull(address)) {
      return bankRepository.findAllByAddressContainingIgnoreCase(address, pageable);
    } else {
      return bankRepository.findAllBy(pageable);
    }
  }
}
