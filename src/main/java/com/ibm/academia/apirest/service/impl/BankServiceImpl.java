package com.ibm.academia.apirest.service.impl;

import com.ibm.academia.apirest.dto.BankDto;
import com.ibm.academia.apirest.exception.NotFoundException;
import com.ibm.academia.apirest.service.BankService;
import com.ibm.academia.apirest.service.CitiBankDataService;
import com.ibm.academia.apirest.util.BankDataFilter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService {

    private static final String NOT_FOUND_ERROR_MSG = "No se encontraron bancos o sucursales cercanas";

    private CitiBankDataService citiBankDataService;

    @Override
    public List<BankDto> findNearBanks(Double latitude, Double longitude, String cp, String state) {
        List<BankDto> collect = citiBankDataService.getBankData().parallelStream()
                .filter(BankDataFilter.getFilter(latitude, longitude, cp, state))
                .collect(Collectors.toList());
        if (collect.isEmpty())
            throw new NotFoundException(NOT_FOUND_ERROR_MSG);
        return collect;
    }
}
