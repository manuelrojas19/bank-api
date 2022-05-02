package com.ibm.academia.apirest.util;

import com.ibm.academia.apirest.dto.BankDto;
import com.ibm.academia.apirest.exception.BadRequestException;

import java.util.Objects;
import java.util.function.Predicate;

public class BankDataFilter {

    public static Predicate<BankDto> getFilter(Double latitude, Double longitude, String cp, String state) {
        Predicate<BankDto> filter;
        if (Objects.nonNull(latitude) && Objects.nonNull(longitude)) {
            filter = bank -> bank.getState().equals(state) && bank.getAddress().contains(cp)
                    && (bank.getGps().getLatitude() - latitude >= -2 && bank.getGps().getLatitude() - latitude <= 2)
                    && (bank.getGps().getLongitude() - longitude >= -2 && bank.getGps().getLongitude() - longitude <= 2);
        } else if (Objects.nonNull(state) && Objects.nonNull(cp)) {
            filter = bank -> bank.getState().equals(state) && bank.getAddress().contains(cp);
        } else if (Objects.nonNull(state)) {
            filter = bank -> bank.getState().equals(state);
        } else if (Objects.nonNull(cp)) {
            filter = bank -> bank.getAddress().contains(cp);
        } else {
            throw new BadRequestException("No se ingresaron parámetros de busqueda");
        }
        return filter;
    }
}
