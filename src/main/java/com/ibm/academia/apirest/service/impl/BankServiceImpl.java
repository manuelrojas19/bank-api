package com.ibm.academia.apirest.service.impl;

import static com.ibm.academia.apirest.utils.Constants.LOCATION_MARGIN;

import com.ibm.academia.apirest.entity.BankEntity;
import com.ibm.academia.apirest.mapper.BankDataMapper;
import com.ibm.academia.apirest.model.*;
import com.ibm.academia.apirest.repository.BankRepository;
import com.ibm.academia.apirest.service.BankService;
import com.ibm.academia.apirest.service.EventHubService;
import java.util.Objects;
import com.ibm.academia.apirest.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService {

  private final BankRepository bankRepository;

  @Override
  @Transactional(readOnly = true)
  public Mono<FindBankResponse> findBanks(
      Pageable pageable,
      Double latitude,
      Double longitude,
      String postalCode,
      String state,
      String address,
      MultiValueMap<String, String> headers) {

    return getBankEntitiesByFilterData(pageable, latitude, longitude, postalCode, state, address)
        .map(BankDataMapper::bankEntityToBankDto)
        .collectList()
        .zipWith(bankRepository.countAllBy())
        .map(tuples -> BankDataMapper.toFindBankResponse(pageable, tuples.getT1(), tuples.getT2()))
        .doOnNext(bank -> log.info(Constants.BANK_INFO_RETRIEVED_LOG_MSG, bank))
        .doOnError(error -> log.error(Constants.BANK_INFO_ERROR_LOG_MSG, error));
  }

  private Flux<BankEntity> getBankEntitiesByFilterData(
      Pageable pageable,
      Double latitude,
      Double longitude,
      String postalCode,
      String state,
      String address) {
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
