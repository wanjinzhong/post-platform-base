package com.neilw.postplatform.base.exception;

public class InvalidMethodException extends RuntimeException {
    public InvalidMethodException() {
    }

    public InvalidMethodException(String message) {
        super(message);
    }

    public InvalidMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMethodException(Throwable cause) {
        super(cause);
    }

    public InvalidMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
