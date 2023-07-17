package com.neilw.postplatform.base.exception.http;

public class UnauthrocationException extends HttpException {
    private static final int httpCode = 403;
    public UnauthrocationException() {
        super(httpCode);
    }

    public UnauthrocationException(String message) {
        super(httpCode, message);
    }

    public UnauthrocationException(String message, Throwable cause) {
        super(httpCode, message, cause);
    }

    public UnauthrocationException(Throwable cause) {
        super(httpCode, cause);
    }

}
