package com.ibm.academia.apirest.dto;

import lombok.*;

@Data
@Builder
public class BankDto {
    private String address;
    private String state;
    private String type;
    private Gps gps;
}
