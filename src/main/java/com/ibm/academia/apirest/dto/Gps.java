package com.ibm.academia.apirest.dto;

import lombok.*;

@Data
@Builder
public class Gps {
    private Double latitude;
    private Double longitude;
}
