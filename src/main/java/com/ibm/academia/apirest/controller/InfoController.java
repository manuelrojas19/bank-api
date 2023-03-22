package com.ibm.academia.apirest.controller;

import com.ibm.academia.apirest.model.ApiInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {

  private @Value("${bank-api.information.host}") String hostName;

  @GetMapping
  public ResponseEntity<ApiInfoResponse> getApiInfo() {
    return ResponseEntity.ok(
        ApiInfoResponse.builder().sourceHostName(hostName).status("OK").build());
  }
}
