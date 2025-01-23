package com.doublevpartners.tutickets.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
  public NotFoundException(String message) {
    super(NOT_FOUND, message);
  }
}
