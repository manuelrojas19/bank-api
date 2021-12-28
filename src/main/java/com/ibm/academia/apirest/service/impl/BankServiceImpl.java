package com.ibm.academia.apirest.service.impl;

import com.ibm.academia.apirest.dto.BankDto;
import com.ibm.academia.apirest.service.BankService;
import com.ibm.academia.apirest.util.BankDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class BankServiceImpl implements BankService {
    private static final String LOCALIZATION_API_URL = "https://www.banamex.com/localizador/jsonP/json5.json";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BankDto> findNearBanks(Double latitude, Double longitude, String cp, String state) {

        ResponseEntity<String> response = restTemplate.getForEntity(LOCALIZATION_API_URL, String.class);

        List<BankDto> bankData;
        try {
            bankData = BankDataUtil.parseBankDataList(new String(response.getBody()
                    .getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return bankData.stream()
                .filter(getFilter(latitude, longitude, cp, state))
                .collect(Collectors.toList());
    }

    private Predicate<BankDto> getFilter(Double latitude, Double longitude, String cp, String state) {
        Predicate<BankDto> filter;
        if (Objects.nonNull(latitude) && Objects.nonNull(longitude)) {
            filter = bank -> bank.getState().equals(state) && bank.getAddress().contains(cp)
                    && (bank.getGps().getLatitude() - latitude >= -2 && bank.getGps().getLatitude() - latitude <= 2)
                    && (bank.getGps().getLongitude() - longitude >= -2 && bank.getGps().getLongitude() - longitude <= 2);
        } else {
            filter = bank -> bank.getState().equals(state) && bank.getAddress().contains(cp);
        }
        return filter;
    }
}
