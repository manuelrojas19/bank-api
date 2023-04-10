package com.manuelr.bank.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {

  @JsonProperty("Latitude")
  Double latitude;

  @JsonProperty("Longitude")
  Double longitude;
}
