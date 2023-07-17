package com.neilw.postplatform.base.exception.http;


public class PageNotFoundException extends HttpException {
    public static final int httpCode = 404;
    public PageNotFoundException() {
        super(httpCode);
    }

    public PageNotFoundException(String message) {
        super(httpCode, message);
    }

    public PageNotFoundException(String message, Throwable cause) {
        super(httpCode, message, cause);
    }

    public PageNotFoundException(Throwable cause) {
        super(httpCode, cause);
    }

}
