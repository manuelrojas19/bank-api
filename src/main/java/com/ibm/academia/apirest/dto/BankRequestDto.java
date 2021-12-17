package com.ibm.academia.apirest.dto;

import lombok.Data;

@Data
public class BankRequestDto {
    private GpsDto gps;
    private String cp;
    private String state;
}
