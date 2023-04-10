package com.manuelr.bank.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location implements Serializable {

  static final long serialVersionUID = -2297135739455100068L;

  Double latitude;

  Double longitude;
}
