package com.doublevpartners.tutickets.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {
  public BadRequestException(String message) {
    super(BAD_REQUEST, message);
  }
}
