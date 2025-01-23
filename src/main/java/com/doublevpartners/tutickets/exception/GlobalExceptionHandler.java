package com.doublevpartners.tutickets.exception;

import static com.doublevpartners.tutickets.util.Constants.DOT;
import static com.doublevpartners.tutickets.util.Constants.MESSAGE;
import static java.util.Objects.requireNonNull;

import jakarta.validation.constraints.NotNull;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    String message = ex.getAllErrors().stream()
      .map(DefaultMessageSourceResolvable::getDefaultMessage)
      .collect(Collectors.joining(", "));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildBody(message + DOT));
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, String>> responseStatusException(@NotNull ResponseStatusException e) {
    return ResponseEntity.status(HttpStatus.valueOf(e.getStatusCode().value())).body(buildBody(requireNonNull(e.getReason())));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGeneralExceptions(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildBody(ex.getMessage()));
  }

  private Map<String, String> buildBody(String message) {
    Map<String, String> body = new HashMap<>();
    body.put(MESSAGE, message);

    return body;
  }
}
