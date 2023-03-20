package com.ibm.academia.apirest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankPage {

    @JsonProperty("Size")
    Integer size;

    @JsonProperty("TotalElements")
    Long totalElements;

    @JsonProperty("TotalPages")
    Integer totalPages;

    @JsonProperty("Number")
    Integer number;

}
