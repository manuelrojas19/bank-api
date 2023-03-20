package com.ibm.academia.apirest.config;

import com.ibm.academia.apirest.service.DataProvisioningService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@AllArgsConstructor
public class DataProvisioningProcessConfiguration {

  private final DataProvisioningService service;

  @PostConstruct
  public void startProvisioningProcess() {
    log.info("Starting data provisioning process");
    service.startProvisioningProcess();
    log.info("Provisioning process was terminated successfully");
  }
}
