package com.ibm.academia.apirest.service.impl;

import static com.ibm.academia.apirest.utils.Constants.ATM_CACHE_NAME;
import static com.ibm.academia.apirest.utils.Constants.LOCATION_MARGIN;

import com.ibm.academia.apirest.entity.BankEntity;
import com.ibm.academia.apirest.mapper.BankDataMapper;
import com.ibm.academia.apirest.model.*;
import com.ibm.academia.apirest.repository.BankRepository;
import com.ibm.academia.apirest.service.BankService;
import com.ibm.academia.apirest.utils.Constants;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService {

  private final BankRepository bankRepository;

  private final ReactiveHashOperations<String, Integer, FindBankResponse> hashOperations;

  @Override
  @Transactional(readOnly = true)
  public Mono<FindBankResponse> findBanks(FindBankFilterData requestData) {
    return hashOperations
        .get(ATM_CACHE_NAME, requestData.hashCode())
        .switchIfEmpty(getFromDatabaseAndCache(requestData))
        .doOnNext(bank -> log.info(Constants.BANK_INFO_RETRIEVED_LOG_MSG, bank))
        .doOnError(error -> log.error(Constants.BANK_INFO_ERROR_LOG_MSG, error));
  }

  private Mono<FindBankResponse> getFromDatabaseAndCache(FindBankFilterData requestData) {
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

  private Flux<BankEntity> getBankEntitiesByFilterData(FindBankFilterData requestData) {

    final var pageable = requestData.getPageable();
    final var latitude = requestData.getLatitude();
    final var longitude = requestData.getLongitude();
    final var postalCode = requestData.getPostalCode();
    final var state = requestData.getState();
    final var address = requestData.getAddress();

    if (Objects.nonNull(latitude) && Objects.nonNull(longitude)) {
      return bankRepository.findAllByLocation_LatitudeBetweenAndLocation_LongitudeBetween(
          latitude - LOCATION_MARGIN,
          latitude + LOCATION_MARGIN,
          longitude - LOCATION_MARGIN,
          longitude + LOCATION_MARGIN,
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
