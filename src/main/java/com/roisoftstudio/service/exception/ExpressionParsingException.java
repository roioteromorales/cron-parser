package com.roisoftstudio.service.exception;

public class ExpressionParsingException extends RuntimeException {

  public ExpressionParsingException(String message) {
    super(message);
  }

  public ExpressionParsingException(String message, Throwable cause) {
    super(message, cause);
  }
}
