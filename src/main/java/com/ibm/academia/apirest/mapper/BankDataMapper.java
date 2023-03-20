package com.ibm.academia.apirest.mapper;

import com.ibm.academia.apirest.entity.BankEntity;
import com.ibm.academia.apirest.model.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;import java.util.stream.Collectors;

public class BankDataMapper {

  public static final String COMMA = ",";
  public static final String EMPTY_STRING = "";
  public static final String POST_CODE_REGEX = "\\d\\d\\d\\d\\d";
  public static final int BANK_DATA_ARRAY_NAME_INDEX = 2;
  public static final int BANK_DATA_ARRAY_STREET_INDEX = 3;
  public static final int BAND_DATA_ARRAY_STREET_INDEX = 4;
  public static final int BANK_DATA_ARRAY_REFERENCES_INDEX = 6;
  public static final int BANK_DATA_ARRAY_PHONE_INDEX = 8;
  public static final String ZERO = "0";
  public static final int BANK_DATA_ARRAY_STATE_INDEX = 17;
  public static final int BANK_DATA_ARRAY_OPENING_INDEX = 13;
  public static final int BANK_DATA_ARRAY_CLOSE_INDEX = 14;
  public static final int BANK_DATA_ARRAY_LONGITUDE_INDEX = 15;
  public static final int BANK_DATA_ARRAY_LATITUDE_INDEX = 16;

  public static BankEntity bankDataArrayToBankEntity(List<String> bankDataArray) {

    Pattern pattern = Pattern.compile(POST_CODE_REGEX);
    Matcher matcher = pattern.matcher(bankDataArray.get(BAND_DATA_ARRAY_STREET_INDEX));

    return BankEntity.builder()
        .name(bankDataArray.get(BANK_DATA_ARRAY_NAME_INDEX))
        .street(bankDataArray.get(BANK_DATA_ARRAY_STREET_INDEX).replace(COMMA, EMPTY_STRING))
        .address(bankDataArray.get(BAND_DATA_ARRAY_STREET_INDEX))
        .references(bankDataArray.get(BANK_DATA_ARRAY_REFERENCES_INDEX))
        .phone(
            bankDataArray.get(BANK_DATA_ARRAY_PHONE_INDEX).equals(ZERO)
                ? null
                : bankDataArray.get(BANK_DATA_ARRAY_PHONE_INDEX))
        .postalCode(matcher.find() ? matcher.group(0) : null)
        .state(bankDataArray.get(BANK_DATA_ARRAY_STATE_INDEX))
        .type(bankDataArray.get(19))
        .openingTime(
            OpeningTime.builder()
                .open(bankDataArray.get(BANK_DATA_ARRAY_OPENING_INDEX))
                .close(bankDataArray.get(BANK_DATA_ARRAY_CLOSE_INDEX))
                .build())
        .location(
            Location.builder()
                .longitude(Double.parseDouble(bankDataArray.get(BANK_DATA_ARRAY_LONGITUDE_INDEX)))
                .latitude(Double.parseDouble(bankDataArray.get(BANK_DATA_ARRAY_LATITUDE_INDEX)))
                .build())
        .build();
  }

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
