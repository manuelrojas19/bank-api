package com.ibm.academia.apirest.controller;

import com.ibm.academia.apirest.model.FindBankResponse;
import com.ibm.academia.apirest.service.BankService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/banks")
public class BankController {

  private final BankService bankService;

  /**
   * EndPoint que retorna una lista de bancos y cajeros cercanos con base en los datos de ubicación
   * ingresados, se requieren dos campos obligatorios, los campos de GPS agregaran más precisión a
   * la búsqueda.
   *
   * @param state cadena opcional con el nombre del estado de la ubicación ingresada.
   * @param postalCode cadena opcional con el CP de la ubicación ingresada.
   * @param latitude valor opcional de la latitud de la ubicación del usuario.
   * @param longitude valor opcional de la longitud de la ubicación del usuario.
   * @return Lista de bancos cercanos a los datos de ubicación ingresados.
   */
  @GetMapping
  public ResponseEntity<FindBankResponse> findNearestBanks(
      Pageable pageable,
      @RequestParam(required = false) String state,
      @RequestParam(required = false) String postalCode,
      @RequestParam(required = false) String address,
      @RequestParam(required = false)
          @Min(value = -90, message = "El valor mínimo de la latitud es -90")
          @Max(value = 90, message = "El valor máximo de la latitud es 90")
          Double latitude,
      @RequestParam(required = false)
          @Min(value = -180, message = "El valor mínimo de la longitud es -180")
          @Max(value = 180, message = "El valor máximo de la longitud es 180")
          Double longitude,
      @RequestHeader MultiValueMap<String, String> headers) {

    ResponseEntity<FindBankResponse> response =
        bankService.findBanks(pageable, latitude, longitude, postalCode, state, address, headers);

    log.info("Sending to the client --> {}", response);

    return response;
  }
}
