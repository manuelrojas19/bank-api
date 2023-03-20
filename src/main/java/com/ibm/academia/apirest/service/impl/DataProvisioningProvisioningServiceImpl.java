package com.ibm.academia.apirest.service.impl;

import com.github.wnameless.json.flattener.FlattenMode;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.flattener.JsonifyArrayList;
import com.ibm.academia.apirest.entity.BankEntity;
import com.ibm.academia.apirest.mapper.BankDataMapper;
import com.ibm.academia.apirest.repository.BankRepository;
import com.ibm.academia.apirest.service.DataProvisioningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataProvisioningProvisioningServiceImpl implements DataProvisioningService {

  public static final int JSONP_JSON_BEGIN_INDEX = 13;

  public static final int JSONP_JSON_END_INDEX = 2;

  private final RestTemplate restTemplate;

  private final BankRepository bankRepository;

  @Value("${web-client.localization.url}")
  private String localizationApiUrl;

  @Autowired
  public DataProvisioningProvisioningServiceImpl(
      RestTemplate restTemplate, BankRepository bankRepository) {
    this.restTemplate = restTemplate;
    this.bankRepository = bankRepository;
  }

  @Override
  @Transactional
  public void startProvisioningProcess() {

    bankRepository.deleteAll();

    log.info("Starting request to citi server for retrieve bank data");

    String response = restTemplate.getForObject(localizationApiUrl, String.class);

    String jsonP =
        new String(
            Objects.requireNonNull(response).getBytes(StandardCharsets.ISO_8859_1),
            StandardCharsets.UTF_8);

    String json = jsonP.substring(JSONP_JSON_BEGIN_INDEX, jsonP.length() - JSONP_JSON_END_INDEX);

    Map<String, Object> flatJson =
        new JsonFlattener(json).withFlattenMode(FlattenMode.KEEP_PRIMITIVE_ARRAYS).flattenAsMap();

    List<BankEntity> bankData =
        flatJson.values().stream()
            .filter(JsonifyArrayList.class::isInstance)
            .map(List.class::cast)
            .map(BankDataMapper::bankDataArrayToBankEntity)
            .collect(Collectors.toList());

    log.info("Retrieved data {}", bankData);

    bankRepository.saveAll(bankData);

    log.info("Data provisioning process executed successfully");
  }
}
