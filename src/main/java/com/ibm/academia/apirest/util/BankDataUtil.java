package com.ibm.academia.apirest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.academia.apirest.dto.BankDto;
import com.ibm.academia.apirest.mapper.BankDataMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BankDataUtil {
    public static List<BankDto> parseBankDataList(String jsonP) throws JsonProcessingException {
        String json = jsonP.substring(13, jsonP.length() - 2);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        List<String[]> bankData = new ArrayList<>();

        // TODO use flat map and filters instead

        node.get("Servicios").elements()
                .forEachRemaining(a -> a.elements()
                        .forEachRemaining(b -> b.elements()
                                .forEachRemaining(c -> bankData.add(mapper.convertValue(c, String[].class))))
                );

        return bankData.stream().map(BankDataMapper::bankDataArrayToBankDataDto)
                .collect(Collectors.toList());
    }
}
