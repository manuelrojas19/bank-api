package com.ibm.academia.apirest.entity;

import com.ibm.academia.apirest.model.Location;
import com.ibm.academia.apirest.model.OpeningTime;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Document
public class BankEntity {

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
