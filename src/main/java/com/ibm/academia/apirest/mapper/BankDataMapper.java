package com.ibm.academia.apirest.mapper;

import com.ibm.academia.apirest.dto.BankDto;
import com.ibm.academia.apirest.dto.Gps;

import java.util.List;

public class BankDataMapper {
    public static BankDto bankDataArrayToBankDataDto(String[] bankDataArray) {
        return BankDto.builder()
                .address(bankDataArray[3] + " " + bankDataArray[4])
                .state(bankDataArray[17])
                .type(bankDataArray[19])
                .gps(Gps.builder()
                        .longitude(Double.parseDouble(bankDataArray[15]))
                        .latitude(Double.parseDouble(bankDataArray[16]))
                        .build())
                .build();
    }

    public static BankDto bankDataArrayToBankDataDto(List<String> bankDataArray) {
        return BankDto.builder()
                .address(bankDataArray.get(3) + " " + bankDataArray.get(4))
                .state(bankDataArray.get(17))
                .type(bankDataArray.get(19))
                .gps(Gps.builder()
                        .longitude(Double.parseDouble(bankDataArray.get(15)))
                        .latitude(Double.parseDouble(bankDataArray.get(16)))
                        .build())
                .build();
    }
}
