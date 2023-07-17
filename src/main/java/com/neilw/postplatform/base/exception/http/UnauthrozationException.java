package com.neilw.postplatform.base.exception.http;

public class UnauthrozationException extends HttpException {
    private static final int httpCode = 401;
    public UnauthrozationException() {
        super(httpCode);
    }

    public UnauthrozationException(String message) {
        super(httpCode, message);
    }

    public UnauthrozationException(String message, Throwable cause) {
        super(httpCode, message, cause);
    }

}
