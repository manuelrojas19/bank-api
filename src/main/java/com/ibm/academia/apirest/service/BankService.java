package com.ibm.academia.apirest.service;

import com.ibm.academia.apirest.dto.BankDto;

import java.util.List;

public interface BankService {
    List<BankDto> findNearBanks(String state, String cp);
}
