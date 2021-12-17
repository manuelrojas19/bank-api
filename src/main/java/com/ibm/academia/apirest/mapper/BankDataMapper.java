package com.ibm.academia.apirest.mapper;

import com.ibm.academia.apirest.dto.BankDto;
import com.ibm.academia.apirest.dto.GpsDto;

public class BankDataMapper {
    public static BankDto bankDataArrayToBankDataDto(String[] bankDataArray) {
        return BankDto.builder()
                .name(bankDataArray[2])
                .address(bankDataArray[3] + " " + bankDataArray[4])
//                .cp(getCp(bankDataArray[4]))
                .state(bankDataArray[17])
                .gps(GpsDto.builder().longitude(bankDataArray[15]).latitude(bankDataArray[16]).build())
                .build();
    }

    private static String getCp(String address) {
        int start = address.indexOf("C.P.");
        return address.substring(start + 5, start + 10);
    }
}
