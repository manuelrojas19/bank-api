package com.ibm.academia.apirest.entity;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;

@Value
@Builder
@Document
public class BankEntity implements Serializable {

  static final long serialVersionUID = 5036535644169621965L;

  @Id String id;

  String name;

  String street;

  String address;

  String references;

  String state;

  String postalCode;

  String type;

  String phone;

  OpeningTime openingTime;

  Location location;
}
