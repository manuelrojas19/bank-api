package com.manuelr.bank.api.mapper;

import com.manuelr.bank.api.entity.BankEntity;
import com.manuelr.bank.api.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BankDataMapper {

  public static BankDto bankEntityToBankDto(BankEntity bankEntity) {
    return BankDto.builder()
        .id(bankEntity.getId())
        .name(bankEntity.getName())
        .references(bankEntity.getReferences())
        .street(bankEntity.getStreet())
        .state(bankEntity.getState())
        .address(bankEntity.getAddress())
        .postalCode(bankEntity.getPostalCode())
        .type(bankEntity.getType())
        .openingTime(
            OpeningTime.builder()
                .open(bankEntity.getOpeningTime().getOpen())
                .close(bankEntity.getOpeningTime().getClose())
                .build())
        .location(
            Location.builder()
                .latitude(bankEntity.getLocation().getLatitude())
                .longitude(bankEntity.getLocation().getLongitude())
                .build())
        .build();
  }

  private static BankData bankDtoListToBankData(List<BankDto> bankDtoList) {
    return BankData.builder().banks(bankDtoList).build();
  }

  public static FindBankResponse toFindBankResponse(
      Pageable pageable, List<BankDto> bankDtoList, Long bankTotalNumber) {
    return FindBankResponse.builder()
        .data(BankDataMapper.bankDtoListToBankData(bankDtoList))
        .page(buildPageResponse(pageable.getPageSize(), pageable.getPageNumber(), bankTotalNumber))
        .build();
  }

  private static BankPage buildPageResponse(
      Integer pageSize, Integer pageNumber, Long totalElements) {
    return BankPage.builder()
        .number(pageNumber)
        .totalPages((int) Math.ceil(totalElements / (double) pageSize))
        .totalElements(totalElements)
        .build();
  }
}
