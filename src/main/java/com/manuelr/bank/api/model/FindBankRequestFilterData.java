package com.manuelr.bank.api.model;

import com.google.common.base.Objects;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

@Value
@Builder
public class FindBankRequestFilterData {

  Pageable pageable;

  Double latitude;

  Double longitude;

  String postalCode;

  String state;

  String address;

  MultiValueMap<String, String> headers;

  @Override
  public int hashCode() {
    return Objects.hashCode(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        latitude,
        longitude,
        postalCode,
        state,
        address);
  }
}
