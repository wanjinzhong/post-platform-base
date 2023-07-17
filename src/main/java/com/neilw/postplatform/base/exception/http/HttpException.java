package com.neilw.postplatform.base.exception.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpException extends RuntimeException {
    private int httpCode;
    public HttpException(int httpCode) {
        this.httpCode = httpCode;
    }

    public HttpException(int httpCode, String message) {
        super(message);
        this.httpCode = httpCode;
    }

    public HttpException(int httpCode, String message, Throwable cause) {
        super(message, cause);
        this.httpCode = httpCode;
    }

    public HttpException(int httpCode, Throwable cause) {
        super(cause);
        this.httpCode = httpCode;
    }

    public static HttpException of(int httpCode, String message, Throwable cause) {
        switch (httpCode) {
            case 400:
                return new BadRequestException(message, cause);
            case 401:
                return new UnauthrozationException(message, cause);
            case 403:
                return new UnauthrocationException(message, cause);
            case 404:
                return new PageNotFoundException(message, cause);
            case 500:
                return new InternalServerErrorException(message, cause);
            default:
                return new HttpException(httpCode, message, cause);
        }
    }
}
