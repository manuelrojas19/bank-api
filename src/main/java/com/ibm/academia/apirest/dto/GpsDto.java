package com.ibm.academia.apirest.dto;

import lombok.*;

@Data
@Builder
public class GpsDto {
    private String latitude;
    private String longitude;
}
