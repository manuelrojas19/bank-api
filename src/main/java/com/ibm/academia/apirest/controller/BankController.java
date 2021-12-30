package com.ibm.academia.apirest.controller;

import com.ibm.academia.apirest.dto.BankDto;
import com.ibm.academia.apirest.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/banks")
@Slf4j
@Validated
public class BankController {

    @Autowired
    private BankService bankService;

    /**
     * EndPoint que retorna una lista de bancos y cajeros cercanos con base en los datos de ubicación
     * ingresados
     *
     * @param state     cadena con el nombre del estado de la ubicación ingresada.
     * @param cp        cadena con el CP de la ubicación ingresada.
     * @param latitude  valor opcional de la latitud de la ubicación del usuario.
     * @param longitude valor opcional de la longitud de la ubicación del usuario.
     * @return Lista de bancos cercanos a los datos de ubicación ingresados.
     */
    @GetMapping
    @Operation(summary = "Obtener una lista de bancos cercanos a la ubicación del cliente")
    @ApiResponse(responseCode = "200", description = "Lista con los bancos cercanos.",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BankDto.class)
            )})
    public ResponseEntity<List<BankDto>> findCloseBanks(@RequestParam String state,
                                                        @RequestParam String cp,
                                                        @RequestParam(required = false)
                                                        @Min(value = -90, message = "El valor mínimo de la latitud es -90")
                                                        @Max(value = 90, message = "El valor máximo de la latitud es 90") Double latitude,
                                                        @RequestParam(required = false)
                                                        @Min(value = -180, message = "El valor mínimo de la longitud es -180")
                                                        @Max(value = 180, message = "El valor máximo de la longitud es 180") Double longitude) {
        log.info("Received data --> {}, {}, {}, {}", state, cp, latitude, longitude);
        List<BankDto> bankDtos = bankService.findNearBanks(latitude, longitude, cp, state);
        log.info("Sending to the client --> {}", bankDtos);
        return new ResponseEntity<>(bankDtos, HttpStatus.OK);
    }
}
