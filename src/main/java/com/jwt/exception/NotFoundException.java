package com.jwt.exception;

public class NotFoundException extends CommonException {

  public NotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
