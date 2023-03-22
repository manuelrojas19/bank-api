package com.ibm.academia.apirest.mapper;

import com.ibm.academia.apirest.entity.BankEntity;
import com.ibm.academia.apirest.model.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class BankDataMapper {

  public static List<BankDto> bankEntityPageToBankDtoList(Page<BankEntity> bankEntityPage) {
    return bankEntityPage.stream()
        .map(BankDataMapper::bankEntityToBankDto)
        .collect(Collectors.toList());
  }

  private static BankDto bankEntityToBankDto(BankEntity bankEntity) {
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

  public static BankData bankDtoListToBankData(List<BankDto> bankDtoList) {
    return BankData.builder().banks(bankDtoList).build();
  }

  public static BankPage bankEntityPageToBankPage(Page<BankEntity> bankEntityPage) {
    return BankPage.builder()
        .number(bankEntityPage.getNumber())
        .size(bankEntityPage.getSize())
        .totalElements(bankEntityPage.getTotalElements())
        .totalPages(bankEntityPage.getTotalPages())
        .build();
  }
}
