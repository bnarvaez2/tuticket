package com.doublevpartners.tutickets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnathorizedException extends ResponseStatusException {

  public UnathorizedException() {
    super(HttpStatus.UNAUTHORIZED, "Incorrect username or password");
  }
}
