package com.ecjtaneo.jwt_auth_demo.exception;

public class RefreshTokenException extends RuntimeException {
  public RefreshTokenException(String message) {
    super(message);
  }
}
