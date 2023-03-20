package com.ibm.academia.apirest.service.impl;

import com.ibm.academia.apirest.entity.BankEntity;
import com.ibm.academia.apirest.mapper.BankDataMapper;
import com.ibm.academia.apirest.model.*;
import com.ibm.academia.apirest.repository.BankRepository;
import com.ibm.academia.apirest.service.BankService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService {

  public static final double LOCATION_MARGIN = 0.05;

  private final BankRepository bankRepository;

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity<FindBankResponse> findBanks(
      Pageable pageable,
      Double latitude,
      Double longitude,
      String postalCode,
      String state,
      String address) {

    Page<BankEntity> bankEntityPage =
        getBankEntitiesByFilterData(pageable, latitude, longitude, postalCode, state, address);

    log.info("Bank data retrieved successfully.");

    List<BankDto> bankDtoList = BankDataMapper.bankEntityPageToBankDtoList(bankEntityPage);

    log.info("Returning bank data to the client.");

    return ResponseEntity.ok(
        FindBankResponse.builder()
            .data(BankDataMapper.bankDtoListToBankData(bankDtoList))
            .page(BankDataMapper.bankEntityPageToBankPage(bankEntityPage))
            .build());
  }

  private Page<BankEntity> getBankEntitiesByFilterData(
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
      return bankRepository.findAll(pageable);
    }
  }
}
