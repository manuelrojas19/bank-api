package com.ibm.academia.apirest.mapper;

import com.ibm.academia.apirest.dto.BankDto;
import com.ibm.academia.apirest.dto.Gps;

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
}
