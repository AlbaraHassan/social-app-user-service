package com.example.userservice.core.exceptions.auth;

import com.example.userservice.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends GeneralException {
  public ForbiddenException() {
    super(HttpStatus.FORBIDDEN.value());
  }

  public ForbiddenException(String message) {
    super(HttpStatus.FORBIDDEN.value(), message);
  }
}
