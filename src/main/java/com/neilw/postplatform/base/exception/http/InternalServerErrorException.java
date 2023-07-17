package com.neilw.postplatform.base.exception.http;

import lombok.Setter;

@Setter
public class InternalServerErrorException extends HttpException {
    private static final int httpCode = 500;
    public InternalServerErrorException() {
        super(httpCode);
    }

    public InternalServerErrorException(String message) {
        super(httpCode, message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(httpCode, message, cause);
    }

    public InternalServerErrorException(Throwable cause) {
        super(httpCode, cause);
    }

}
