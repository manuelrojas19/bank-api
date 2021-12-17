package com.ibm.academia.apirest.dto;

import lombok.*;

@Data
@Builder
public class BankDto {
    private String name;
    private String address;
    private String state;
    private GpsDto gps;
}
