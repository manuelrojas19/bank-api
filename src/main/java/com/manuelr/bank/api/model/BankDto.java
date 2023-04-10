package com.manuelr.bank.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankDto {

  @JsonProperty("Id")
  String id;

  @JsonProperty("Name")
  String name;

  @JsonProperty("Street")
  String street;

  @JsonProperty("Address")
  String address;

  @JsonProperty("References")
  String references;

  @JsonProperty("State")
  String state;

  @JsonProperty("PostalCode")
  String postalCode;

  @JsonProperty("Type")
  String type;

  @JsonProperty("PhoneNumber")
  String phoneNumber;

  @JsonProperty("OpeningTime")
  OpeningTime openingTime;

  @JsonProperty("Location")
  Location location;
}
