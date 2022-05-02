package com.ibm.academia.apirest.service.impl;

import com.github.wnameless.json.flattener.FlattenMode;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.flattener.JsonifyArrayList;
import com.ibm.academia.apirest.dto.BankDto;
import com.ibm.academia.apirest.mapper.BankDataMapper;
import com.ibm.academia.apirest.service.CitiBankDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CitiBankDataServiceImpl implements CitiBankDataService {

    private static final String LOCALIZATION_API_URL = "https://www.banamex.com/localizador/jsonP/json5.json";

    private final RestTemplate restTemplate;

    public List<BankDto> getBankData() {
        ResponseEntity<String> response = restTemplate.getForEntity(LOCALIZATION_API_URL, String.class);

        String jsonP = new String(Objects.requireNonNull(response.getBody())
                .getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        String json = jsonP.substring(13, jsonP.length() - 2);

        Map<String, Object> flatJson = new JsonFlattener(json)
                .withFlattenMode(FlattenMode.KEEP_PRIMITIVE_ARRAYS)
                .flattenAsMap();

        return flatJson.values().parallelStream()
                .filter(JsonifyArrayList.class::isInstance)
                .map(List.class::cast)
                .map(BankDataMapper::bankDataArrayToBankDataDto)
                .collect(Collectors.toList());
    }
}
