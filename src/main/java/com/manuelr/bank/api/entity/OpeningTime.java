package com.manuelr.bank.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpeningTime implements Serializable {

  static final long serialVersionUID = 3104110741613031104L;

  String open;
  
  String close;
}
