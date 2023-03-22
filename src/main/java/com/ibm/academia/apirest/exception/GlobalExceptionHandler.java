package com.ibm.academia.apirest.exception;

import com.ibm.academia.apirest.model.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  private static final String NOT_READABLE_ERROR_MSG = "No se ingresaron datos o son incorrectos";

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<ExceptionResponseDto> notFoundHandler(NotFoundException e) {
    log.error(e.getMessage());
    ExceptionResponseDto response = ExceptionResponseDto.builder().message(e.getMessage()).build();
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  public ResponseEntity<ExceptionResponseDto> notReadableBodyHandler(
      HttpMessageNotReadableException e) {
    log.error(e.getMessage());
    ExceptionResponseDto response =
        ExceptionResponseDto.builder().message(NOT_READABLE_ERROR_MSG).build();
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = MissingServletRequestParameterException.class)
  public ResponseEntity<ExceptionResponseDto> missingParameter(
      MissingServletRequestParameterException e) {
    log.error(e.getMessage());
    ExceptionResponseDto response = ExceptionResponseDto.builder().message(e.getMessage()).build();
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ExceptionResponseDto> methodArgumentTypeMismatchExceptionHandler(
      MethodArgumentTypeMismatchException e) {
    log.error(e.getMessage());
    ExceptionResponseDto response =
        ExceptionResponseDto.builder().message(NOT_READABLE_ERROR_MSG).build();
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = ConstraintViolationException.class)
  public ResponseEntity<ExceptionResponseDto> constraintViolationExceptionHandler(
      ConstraintViolationException e) {
    log.error(e.getMessage());
    ExceptionResponseDto response = ExceptionResponseDto.builder().message(e.getMessage()).build();
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
