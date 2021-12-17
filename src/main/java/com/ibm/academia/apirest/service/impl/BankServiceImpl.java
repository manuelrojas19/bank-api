package com.ibm.academia.apirest.service.impl;

import com.ibm.academia.apirest.dto.BankDto;
import com.ibm.academia.apirest.service.BankService;
import com.ibm.academia.apirest.util.BankDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BankServiceImpl implements BankService {

    private static final String LOCALIZATION_API_URL = "https://www.banamex.com/localizador/jsonP/json5.json";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BankDto> findNearBanks(String state, String cp) {
        ResponseEntity<String> response = restTemplate.getForEntity(LOCALIZATION_API_URL, String.class);
        List<BankDto> bankData;
        try {
            bankData = BankDataUtil.parseBankDataList(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<BankDto> nearBanks = bankData.stream().filter(
                bank -> bank.getState().equals(state)
                        && bank.getAddress().contains(cp))
                .collect(Collectors.toList());
        return nearBanks;
    }
}
