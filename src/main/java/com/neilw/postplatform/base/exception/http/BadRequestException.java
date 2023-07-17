package com.neilw.postplatform.base.exception.http;

public class BadRequestException extends HttpException {
    private static final int httpCode = 400;
    public BadRequestException() {
        super(httpCode);
    }

    public BadRequestException(String message) {
        super(httpCode, message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(httpCode, message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(httpCode, cause);
    }
}
